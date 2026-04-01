package com.projetofmds.risk_analysis_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RiskAnalysisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskAnalysisServiceApplication.class, args);
	}

}
