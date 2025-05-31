package com.agibank.insurance.service;

import com.agibank.insurance.model.Insurance;

public interface InsuranceContractService {
    Insurance contractInsurance(String cpf, String type);
    Insurance contractInsuranceFallback(String cpf, String type, Exception e);
} 