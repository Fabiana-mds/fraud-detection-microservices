package com.projetofmds.transaction_service.controller;

import com.projetofmds.transaction_service.model.Transaction;
import com.projetofmds.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//import java.util.List;

@RestController
@RequestMapping("/transactions") 
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        log.info("Recebendo nova transação para a conta ID: {}", transaction.getAccountId());
        return transactionService.createTransaction(transaction);
    }
    
    // Futuramente adicionaremos o GET aqui
}