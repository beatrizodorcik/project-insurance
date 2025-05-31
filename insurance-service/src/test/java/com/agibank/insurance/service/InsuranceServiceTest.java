package com.agibank.insurance.service;

import com.agibank.insurance.dto.InsuranceSimulationRequest;
import com.agibank.insurance.dto.InsuranceSimulationResponse;
import com.agibank.insurance.model.Insurance;
import com.agibank.insurance.model.InsuranceType;
import com.agibank.insurance.repository.InsuranceRepository;
import com.agibank.insurance.model.plan.InsurancePlanFactory;
import com.agibank.insurance.model.plan.BronzePlan;
import com.agibank.insurance.model.plan.SilverPlan;
import com.agibank.insurance.model.plan.GoldPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private InsurancePlanFactory planFactory;

    @InjectMocks
    private InsuranceService insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void simulateInsurance_Under30_ReturnsBronze() {
        // Arrange
        InsuranceSimulationRequest request = new InsuranceSimulationRequest();
        request.cpf = "12345678901";
        request.age = 25;
        when(restTemplate.getForObject(anyString(), any())).thenReturn(true);
        when(planFactory.getPlanForAge(25)).thenReturn(new BronzePlan());

        // Act
        InsuranceSimulationResponse result = insuranceService.simulate(request);

        // Assert
        assertNotNull(result);
        assertEquals("Bronze", result.getType());
        assertEquals(50.0, result.getMonthlyFee());
        assertEquals(100000.0, result.getInsuredAmount());
        assertEquals(InsuranceType.BRONZE.getBenefits(), result.getBenefits());
    }

    @Test
    void simulateInsurance_Between30And59_ReturnsSilver() {
        // Arrange
        InsuranceSimulationRequest request = new InsuranceSimulationRequest();
        request.cpf = "12345678901";
        request.age = 45;
        when(restTemplate.getForObject(anyString(), any())).thenReturn(true);
        when(planFactory.getPlanForAge(45)).thenReturn(new SilverPlan());

        // Act
        InsuranceSimulationResponse result = insuranceService.simulate(request);

        // Assert
        assertNotNull(result);
        assertEquals("Silver", result.getType());
        assertEquals(100.0, result.getMonthlyFee());
        assertEquals(150000.0, result.getInsuredAmount());
        assertEquals(InsuranceType.SILVER.getBenefits(), result.getBenefits());
    }

    @Test
    void simulateInsurance_Over60_ReturnsGold() {
        // Arrange
        InsuranceSimulationRequest request = new InsuranceSimulationRequest();
        request.cpf = "12345678901";
        request.age = 65;
        when(restTemplate.getForObject(anyString(), any())).thenReturn(true);
        when(planFactory.getPlanForAge(65)).thenReturn(new GoldPlan());

        // Act
        InsuranceSimulationResponse result = insuranceService.simulate(request);

        // Assert
        assertNotNull(result);
        assertEquals("Gold", result.getType());
        assertEquals(200.0, result.getMonthlyFee());
        assertEquals(200000.0, result.getInsuredAmount());
        assertEquals(InsuranceType.GOLD.getBenefits(), result.getBenefits());
    }

    @Test
    void contractInsurance_Success() {
        // Arrange
        String cpf = "12345678901";
        String type = "SILVER";
        when(restTemplate.getForObject(anyString(), any())).thenReturn(true);
        when(planFactory.getPlanByType("SILVER")).thenReturn(new SilverPlan());
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Insurance result = insuranceService.contractInsurance(cpf, type);

        // Assert
        assertNotNull(result);
        assertEquals(cpf, result.getCpf());
        assertEquals("SILVER", result.getType());
        assertEquals(100.0, result.getMonthlyFee());
        assertEquals(150000.0, result.getInsuredAmount());
        assertEquals(InsuranceType.SILVER.getBenefits(), result.getBenefits());
        verify(insuranceRepository).save(any(Insurance.class));
    }
} 