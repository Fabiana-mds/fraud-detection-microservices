package com.projetofmds.transaction_service.client;

import com.projetofmds.transaction_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// AQUI ESTÁ O TRUQUE: Adicione o url = "http://localhost:8080"
@FeignClient(name = "account-service", url = "http://localhost:8080")
public interface AccountClient {

    @GetMapping("/accounts/{id}/risk-profile")
    RiskProfileDTO getAccountRiskProfile(@PathVariable("id") Long id);
}