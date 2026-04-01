package com.projetofmds.risk_analysis_service.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import com.projetofmds.risk_analysis_service.dto.TransactionDTO;

@Component
public class NightTimeStrategy implements RiskStrategy {
    @Override
    public BigDecimal check(TransactionDTO transaction) {
        int hour = transaction.timestamp().getHour();
        // Entre 22h e 05h é considerado horário de risco (mais 15 pontos)
        return (hour >= 22 || hour <= 5) 
               ? new BigDecimal("15.0") : BigDecimal.ZERO;
    }
    @Override
    public String getName() { return "Night Time Transaction"; }
}
