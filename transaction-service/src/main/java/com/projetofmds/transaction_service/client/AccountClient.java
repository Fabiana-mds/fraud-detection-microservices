package com.projetofmds.transaction_service.client;

import com.projetofmds.transaction_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts/{id}/risk-profile")
    RiskProfileDTO getAccountRiskProfile(@PathVariable("id") Long id);
}