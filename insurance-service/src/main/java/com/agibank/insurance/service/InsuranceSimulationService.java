package com.agibank.insurance.service;

import com.agibank.insurance.dto.InsuranceSimulationRequest;
import com.agibank.insurance.dto.InsuranceSimulationResponse;

public interface InsuranceSimulationService {
    InsuranceSimulationResponse simulate(InsuranceSimulationRequest request);
} 