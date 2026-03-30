package com.projetofmds.account_service.dto;

import java.math.BigDecimal;

public record RiskProfileDTO(
    Long accountId,
    String customerName,
    BigDecimal currentRiskScore,
    String riskStatus 
) {}
