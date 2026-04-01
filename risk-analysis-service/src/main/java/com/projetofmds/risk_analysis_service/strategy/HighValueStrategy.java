package com.projetofmds.risk_analysis_service.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import com.projetofmds.risk_analysis_service.dto.TransactionDTO;

@Component
public class HighValueStrategy implements RiskStrategy {
    @Override
    public BigDecimal check(TransactionDTO transaction) {
        // Se for maior que 5000, adiciona 10 pontos de risco
        return transaction.amount().compareTo(new BigDecimal("5000")) > 0 
               ? new BigDecimal("10.0") : BigDecimal.ZERO;
    }
    @Override
    public String getName() { return "High Value Transaction"; }
}