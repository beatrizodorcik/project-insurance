package com.agibank.client.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String cpf) {
        super("Client not found with CPF: " + cpf);
    }
} 