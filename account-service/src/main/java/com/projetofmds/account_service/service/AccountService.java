package com.projetofmds.account_service.service;

import com.projetofmds.account_service.dto.RiskProfileDTO;
import com.projetofmds.account_service.model.Account;
import com.projetofmds.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Account account) {
        if (account.getBaseRiskScore() == null) {
            account.setBaseRiskScore(new BigDecimal("50.00")); // Score inicial padrão
        }
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RiskProfileDTO getRiskProfile(Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        // Lógica simplificada: se o score for maior que 200, status é HIGH (Alto Risco)
        String status = account.getBaseRiskScore().compareTo(new BigDecimal("200")) > 0 ? "HIGH" : "NORMAL";

        return new RiskProfileDTO(
            account.getId(),
            account.getCostumerName(),
            account.getBaseRiskScore(),
            status
        );
    }
}