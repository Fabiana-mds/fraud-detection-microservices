package com.projetofmds.account_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts") 
public class AccountTestController {

    @GetMapping("/status")
    public String status() {
        return "Account Service is UP and running!";
    }
}