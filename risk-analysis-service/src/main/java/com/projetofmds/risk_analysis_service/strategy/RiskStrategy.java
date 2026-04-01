package com.projetofmds.risk_analysis_service.strategy;

import com.projetofmds.risk_analysis_service.dto.TransactionDTO;
import java.math.BigDecimal;

public interface RiskStrategy {
    BigDecimal check(TransactionDTO transaction);
    String getName();
}
