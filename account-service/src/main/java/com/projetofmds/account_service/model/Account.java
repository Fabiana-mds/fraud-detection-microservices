package com.projetofmds.account_service.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "accounts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include 
    private Long id; 

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "costumer_name", nullable = false)
    private String customerName;

    @Column(nullable = false)
    private BigDecimal baseRiskScore;
  
}

