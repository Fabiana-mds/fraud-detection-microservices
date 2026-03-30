package com.projetofmds.transaction_service.model; 
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.projetofmds.transaction_service.model.enums.TransactionStatus;
import com.projetofmds.transaction_service.model.enums.TransactionType;

@Entity
@Table(name = "transactions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Guardamos apenas o ID da conta que vem do outro serviço
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
}