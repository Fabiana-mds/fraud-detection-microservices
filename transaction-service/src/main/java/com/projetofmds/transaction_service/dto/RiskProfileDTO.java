package com.projetofmds.transaction_service.dto;

public record RiskProfileDTO(

    Long accountId,
    String riskStatus,
    Double currentBalance
) {}
