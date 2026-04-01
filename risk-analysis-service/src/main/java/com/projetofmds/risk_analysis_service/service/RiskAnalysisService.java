package com.projetofmds.risk_analysis_service.service;

import com.projetofmds.risk_analysis_service.dto.TransactionDTO;
import com.projetofmds.risk_analysis_service.strategy.RiskStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskAnalysisService {

    // O Spring injeta automaticamente todas as classes que implementam RiskStrategy
    private final List<RiskStrategy> strategies;

    public BigDecimal evaluateTransaction(TransactionDTO transaction) {
        return strategies.stream()
                .map(strategy -> strategy.check(transaction))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}