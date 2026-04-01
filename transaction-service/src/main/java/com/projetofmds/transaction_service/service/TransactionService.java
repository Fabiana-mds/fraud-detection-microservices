package com.projetofmds.transaction_service.service;

import com.projetofmds.transaction_service.client.AccountClient;
import com.projetofmds.transaction_service.client.RiskClient;
import com.projetofmds.transaction_service.dto.TransactionRequestDTO;
import com.projetofmds.transaction_service.model.Transaction;
import com.projetofmds.transaction_service.model.enums.TransactionStatus;
import com.projetofmds.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final RiskClient riskClient;

    public Transaction createTransaction(Transaction transaction) {
        log.info("Iniciando validação completa da transação para conta: {}", transaction.getAccountId());

        // Definindo as réguas de segurança
        BigDecimal MAX_RISK_SCORE = new BigDecimal("30");
        BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("10000"); // 10 mil reais

        try {
            // 1. Busca perfil da conta
            var accountProfile = accountClient.getAccountRiskProfile(transaction.getAccountId());
            BigDecimal baseScore = accountProfile.baseRiskScore();
            
            if (baseScore == null) {
                baseScore = BigDecimal.ZERO;
            }

            log.info("Score base da conta: {}", baseScore);
        
            // 2. Prepara e chama o motor de risco externo
            TransactionRequestDTO ricoRequest = new TransactionRequestDTO(
                transaction.getAccountId(), 
                transaction.getAmount(), 
                LocalDateTime.now() 
            );

            BigDecimal transactionScore = riskClient.evaluateTransaction(ricoRequest);
            log.info("Score da transação calculado pelo motor: {}", transactionScore);

            // 3. Calcula Risco Total
            BigDecimal totalRisk = baseScore.add(transactionScore);
            log.info("Risco Total Consolidado: {} | Valor: {}", totalRisk, transaction.getAmount());

            // --- NOVA LÓGICA DE DECISÃO ---
            boolean isHighRiskScore = totalRisk.compareTo(MAX_RISK_SCORE) > 0;
            boolean isHighValue = transaction.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0;

            if (isHighRiskScore || isHighValue) {
                log.warn("Transação REJEITADA! Motivo: {}", isHighValue ? "Valor acima do limite" : "Risco elevado");
                transaction.setStatus(TransactionStatus.REJECTED);
            } else {
                log.info("Transação APROVADA dentro dos critérios de segurança.");
                transaction.setStatus(TransactionStatus.APPROVED);
            }

        } catch (Exception e) {
            log.error("Erro na comunicação entre serviços: {}", e.getMessage());
            throw new RuntimeException("Falha na análise de fraude. Tente novamente.");
        }

        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
}