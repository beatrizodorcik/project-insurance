package com.agibank.insurance.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ClientValidationService {
    private final RestTemplate restTemplate;
    private final String clientServiceUrl;

    public ClientValidationService(
            RestTemplate restTemplate,
            @Value("${client.service.url}") String clientServiceUrl) {
        this.restTemplate = restTemplate;
        this.clientServiceUrl = clientServiceUrl;
    }

    public void validateClient(String cpf) {
        log.info("Validating client with CPF: {}", cpf);
        String url = clientServiceUrl + "/api/clients/" + cpf;
        try {
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            log.error("Error checking client existence: {}", e.getMessage());
            throw new RuntimeException("Client not found or service unavailable");
        }
    }
} 