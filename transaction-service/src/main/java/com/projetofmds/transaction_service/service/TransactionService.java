package com.projetofmds.transaction_service.service;

import com.projetofmds.transaction_service.client.AccountClient;
import com.projetofmds.transaction_service.model.Transaction;
import com.projetofmds.transaction_service.model.enums.TransactionStatus;
import com.projetofmds.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient; 

    public Transaction createTransaction(Transaction transaction) {
        log.info("Iniciando validação da transação para conta: {}",transaction.getAccountId());
        
        //Validação da conta
        try {
            accountClient.getAccountRiskProfile(transaction.getAccountId());
            log.info("Conta verificada com sucesso via Feign!");
        } catch (Exception e) {
            log.error("Falha ao validar conta: {}", e.getMessage());
            throw new RuntimeException("Transação negada: Conta inexistente.");
        }

        // Se a conta existe, prosseguimento da transação
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);

        return transactionRepository.save(transaction);
    }
}