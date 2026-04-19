package com.projetofmds.transaction_service.service;

import com.projetofmds.transaction_service.client.AccountClient;
import com.projetofmds.transaction_service.client.RiskClient;
import com.projetofmds.transaction_service.dto.TransactionRequestDTO;
import com.projetofmds.transaction_service.model.Transaction;
import com.projetofmds.transaction_service.model.enums.TransactionStatus;
import com.projetofmds.transaction_service.repository.TransactionRepository;
import feign.FeignException; 
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final RiskClient riskClient;

    @Value("${app.rules.max-amount:1000}")
    private BigDecimal maxAmount;

    @Value("${app.rules.max-risk-score:50}")
    private BigDecimal maxRiskScore;

    // --- CORREÇÃO AQUI: Construtor Manual para substituir o Lombok ---
    public TransactionService(TransactionRepository transactionRepository, 
                              AccountClient accountClient, 
                              RiskClient riskClient) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
        this.riskClient = riskClient;
    }

    public Transaction createTransaction(Transaction transaction) {
        // Removi as chamadas de 'log' porque o Lombok/Slf4j foi desativado
        
        BigDecimal baseScore = BigDecimal.ZERO;
        BigDecimal transactionScore = BigDecimal.ZERO;

        try {
            // 1. Busca perfil da conta
            try {
                var accountProfile = accountClient.getAccountRiskProfile(transaction.getAccountId());
                baseScore = (accountProfile.baseRiskScore() != null) ? accountProfile.baseRiskScore() : BigDecimal.ZERO;
            } catch (FeignException e) {
                baseScore = BigDecimal.ZERO;
            }

            // 2. Prepara e chama o motor de risco
            TransactionRequestDTO ricoRequest = new TransactionRequestDTO(
                transaction.getAccountId(), 
                transaction.getAmount(), 
                LocalDateTime.now() 
            );

            try {
                transactionScore = riskClient.evaluateTransaction(ricoRequest);
            } catch (FeignException e) {
                transactionScore = new BigDecimal("50"); 
            }

            // 3. Calcula Risco Total
            BigDecimal totalRisk = baseScore.add(transactionScore);

            // 4. Lógica de Decisão
            boolean isHighRiskScore = totalRisk.compareTo(maxRiskScore) > 0;
            boolean isHighValue = transaction.getAmount().compareTo(maxAmount) > 0;

            if (isHighRiskScore || isHighValue) {
                transaction.setStatus(TransactionStatus.REJECTED);
            } else {
                transaction.setStatus(TransactionStatus.APPROVED);
            }

        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.REJECTED);
        }

        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
}