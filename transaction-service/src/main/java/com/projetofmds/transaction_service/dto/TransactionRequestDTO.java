package com.projetofmds.transaction_service.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO(
    Long accountId,
    BigDecimal amount,
    java.time.LocalDateTime timestamp 
) {}