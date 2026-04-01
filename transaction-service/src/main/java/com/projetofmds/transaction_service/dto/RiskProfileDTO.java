package com.projetofmds.transaction_service.dto;

import java.math.BigDecimal;

public record RiskProfileDTO(
    Long accountId,
    BigDecimal baseRiskScore, // O campo que estava faltando!
    String riskStatus
) {}