package com.agibank.insurance.exception;

public class InsuranceException extends RuntimeException {
    public InsuranceException(String message) {
        super(message);
    }

    public InsuranceException(String message, Throwable cause) {
        super(message, cause);
    }
} 