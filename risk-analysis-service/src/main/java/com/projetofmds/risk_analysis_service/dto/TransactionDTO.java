package com.projetofmds.risk_analysis_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
    Long accountId,
    BigDecimal amount,
    LocalDateTime timestamp
) {}