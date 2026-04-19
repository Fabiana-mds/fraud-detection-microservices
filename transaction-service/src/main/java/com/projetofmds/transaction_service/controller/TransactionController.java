package com.projetofmds.transaction_service.controller;

import com.projetofmds.transaction_service.model.Transaction;
import com.projetofmds.transaction_service.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    // --- CONSTRUTOR MANUAL (Substitui o RequiredArgsConstructor do Lombok) ---
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        // Log removido temporariamente pois o Slf4j (Lombok) foi desativado
        return transactionService.createTransaction(transaction);
    }
    
    // Futuramente adicionaremos o GET aqui
}