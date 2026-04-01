package com.projetofmds.transaction_service.client;

import com.projetofmds.transaction_service.dto.TransactionRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.math.BigDecimal;

@FeignClient(name = "risk-analysis-service") // Nome que está no Eureka!
public interface RiskClient {

    @PostMapping("/risk-analysis/evaluate")
    BigDecimal evaluateTransaction(@RequestBody TransactionRequestDTO transaction);
}
