package com.projetofmds.transaction_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.projetofmds.transaction_service.model.enums.TransactionStatus;
import com.projetofmds.transaction_service.model.enums.TransactionType;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    // Construtor Padrão
    public Transaction() {}

    // Construtor Completo
    public Transaction(Long id, Long accountId, BigDecimal amount, LocalDateTime timestamp, TransactionStatus status, TransactionType type) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
        this.type = type;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
}