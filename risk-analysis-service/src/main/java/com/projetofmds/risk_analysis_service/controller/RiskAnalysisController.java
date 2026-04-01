package com.projetofmds.risk_analysis_service.controller;

import com.projetofmds.risk_analysis_service.dto.TransactionDTO;
import com.projetofmds.risk_analysis_service.service.RiskAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/risk-analysis")
@RequiredArgsConstructor
public class RiskAnalysisController {

    private final RiskAnalysisService riskService;

    @PostMapping("/evaluate")
    public BigDecimal evaluate(@RequestBody TransactionDTO transaction) {
        // Recebe a transação e devolve a soma dos pontos de risco
        return riskService.evaluateTransaction(transaction);
    }
}
