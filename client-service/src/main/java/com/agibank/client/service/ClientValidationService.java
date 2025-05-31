package com.agibank.client.service;

import com.agibank.client.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientValidationService {
    
    public void validateClient(Client client) {
        log.info("Validating client with CPF: {}", client.getCpf());
        validateRequiredFields(client);
        validateCpf(client.getCpf());
        validatePhone(client.getPhone());
        validateBirthDate(client.getBirthDate());
    }

    private void validateRequiredFields(Client client) {
        if (client.getCpf() == null || client.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF is required");
        }
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (client.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date is required");
        }
    }

    private void validateCpf(String cpf) {
        if (cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid CPF format");
        }
    }

    private void validatePhone(String phone) {
        if (phone != null && !phone.matches("^\\+?[1-9]\\d{1,14}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    private void validateBirthDate(java.time.LocalDate birthDate) {
        if (birthDate.isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }
} 