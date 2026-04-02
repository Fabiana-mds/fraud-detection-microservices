package com.projetofmds.transaction_service.service;

import com.projetofmds.transaction_service.client.AccountClient;
import com.projetofmds.transaction_service.client.RiskClient;
import com.projetofmds.transaction_service.dto.TransactionRequestDTO;
import com.projetofmds.transaction_service.model.Transaction;
import com.projetofmds.transaction_service.model.enums.TransactionStatus;
import com.projetofmds.transaction_service.repository.TransactionRepository;
import feign.FeignException; 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final RiskClient riskClient;

    @Value("${app.rules.max-amount}")
    private BigDecimal maxAmount;

    @Value("${app.rules.max-risk-score}")
    private BigDecimal maxRiskScore;

    public Transaction createTransaction(Transaction transaction) {
        log.info("Iniciando validação completa da transação para conta: {}", transaction.getAccountId());

        BigDecimal baseScore = BigDecimal.ZERO;
        BigDecimal transactionScore = BigDecimal.ZERO;

        try {
            // 1. Busca perfil da conta
            try {
                var accountProfile = accountClient.getAccountRiskProfile(transaction.getAccountId());
                baseScore = (accountProfile.baseRiskScore() != null) ? accountProfile.baseRiskScore() : BigDecimal.ZERO;
                log.info("Score base da conta obtido: {}", baseScore);
            } catch (FeignException e) {
                log.error("Falha ao comunicar com AccountService. Usando score base 0.");
                baseScore = BigDecimal.ZERO;
            }

            // 2. Prepara e chama o motor de risco (Risk Analysis Service)
            TransactionRequestDTO ricoRequest = new TransactionRequestDTO(
                transaction.getAccountId(), 
                transaction.getAmount(), 
                LocalDateTime.now() 
            );

            try {
                transactionScore = riskClient.evaluateTransaction(ricoRequest);
                log.info("Score da transação calculado pelo motor: {}", transactionScore);
            } catch (FeignException e) {
                log.error("Risk Analysis Service offline! Aplicando score de contingência (50) para segurança.");
                // Se o motor cair, atribuímos um risco alto para evitar fraudes enquanto estamos "cegos"
                transactionScore = new BigDecimal("50"); 
            }

            // 3. Calcula Risco Total
            BigDecimal totalRisk = baseScore.add(transactionScore);
            log.info("Risco Total Consolidado: {} | Valor: {}", totalRisk, transaction.getAmount());

            // 4. Lógica de Decisão
            boolean isHighRiskScore = totalRisk.compareTo(maxRiskScore) > 0;
            boolean isHighValue = transaction.getAmount().compareTo(maxAmount) > 0;

            if (isHighRiskScore || isHighValue) {
                String motivo = isHighValue ? "Valor acima do limite" : "Risco elevado (" + totalRisk + ")";
                log.warn("Transação REJEITADA! Motivo: {}", motivo);
                transaction.setStatus(TransactionStatus.REJECTED);
            } else {
                log.info("Transação APROVADA!");
                transaction.setStatus(TransactionStatus.APPROVED);
            }

        } catch (Exception e) {
            log.error("Erro inesperado no fluxo de transação: {}", e.getMessage());
            // Aqui você pode decidir se aprova ou rejeita em caso de erro crítico
            transaction.setStatus(TransactionStatus.REJECTED);
        }

        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
}