package com.projetofmds.transaction_service.repository;

import com.projetofmds.transaction_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Aqui no futuro podemos criar métodos como findByAccountId
}
