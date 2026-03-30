package com.projetofmds.account_service.controller;

import com.projetofmds.account_service.dto.RiskProfileDTO;
import com.projetofmds.account_service.model.Account;
import com.projetofmds.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        log.info("Criando nova conta para o cliente: {}", account.getCostumerName());
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        log.info("Listando todas as contas");
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}/risk-profile")
    public ResponseEntity<RiskProfileDTO> getRiskProfile(@PathVariable Long id) {
        log.info("Consultando perfil de risco da conta ID: {}", id);
        RiskProfileDTO profile = accountService.getRiskProfile(id);
        return ResponseEntity.ok(profile);
    }
}