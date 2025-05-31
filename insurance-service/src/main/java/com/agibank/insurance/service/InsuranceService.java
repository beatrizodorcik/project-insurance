package com.agibank.insurance.service;

import com.agibank.insurance.dto.InsuranceSimulationRequest;
import com.agibank.insurance.dto.InsuranceSimulationResponse;
import com.agibank.insurance.model.Insurance;
import com.agibank.insurance.model.plan.InsurancePlan;
import com.agibank.insurance.model.plan.InsurancePlanFactory;
import com.agibank.insurance.repository.InsuranceRepository;
import com.agibank.insurance.exception.InsuranceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class InsuranceService implements InsuranceSimulationService, InsuranceContractService {

    private static final Logger logger = LoggerFactory.getLogger(InsuranceService.class);

    private final InsuranceRepository insuranceRepository;
    private final RestTemplate restTemplate;
    private final String clientServiceUrl;
    private final InsurancePlanFactory planFactory;

    public InsuranceService(InsuranceRepository insuranceRepository,
                          RestTemplate restTemplate,
                          @Value("${client.service.url}") String clientServiceUrl,
                          InsurancePlanFactory planFactory) {
        this.insuranceRepository = insuranceRepository;
        this.restTemplate = restTemplate;
        this.clientServiceUrl = clientServiceUrl;
        this.planFactory = planFactory;
    }

    @Override
    public InsuranceSimulationResponse simulate(InsuranceSimulationRequest request) {
        log.info("Simulating insurance for CPF: {}", request.cpf);
        var plan = planFactory.getPlanForAge(request.age);
        return InsuranceSimulationResponse.fromPlan(plan);
    }

    @Override
    @CircuitBreaker(name = "clientService", fallbackMethod = "contractInsuranceFallback")
    @Retry(name = "clientService")
    public Insurance contractInsurance(String cpf, String type) {
        log.info("Contracting insurance for CPF: {}", cpf);
        validateClient(cpf);
        try {
            var plan = planFactory.getPlanByType(type);
            return createAndSaveInsurance(cpf, plan);
        } catch (IllegalArgumentException e) {
            throw new InsuranceException(e.getMessage(), e);
        }
    }

    @Override
    public Insurance contractInsuranceFallback(String cpf, String type, Exception e) {
        log.warn("Fallback: Contracting insurance without client validation for CPF: {}", cpf);
        var plan = planFactory.getPlanByType(type);
        return createAndSaveInsurance(cpf, plan);
    }

    private Insurance createAndSaveInsurance(String cpf, InsurancePlan plan) {
        Insurance insurance = new Insurance(cpf, plan);
        return insuranceRepository.save(insurance);
    }

    private void validateClient(String cpf) {
        try {
            Boolean exists = restTemplate.getForObject(
                clientServiceUrl + "/api/clients/" + cpf,
                Boolean.class
            );
            if (exists == null || !exists) {
                throw new InsuranceException("Client not found: " + cpf);
            }
        } catch (InsuranceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error checking client existence: {}", e.getMessage());
            throw new InsuranceException("Error validating client: " + e.getMessage(), e);
        }
    }
}