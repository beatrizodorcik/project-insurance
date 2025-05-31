package com.agibank.insurance.integration;

import com.agibank.insurance.config.TestConfig;
import com.agibank.insurance.dto.InsuranceSimulationRequest;
import com.agibank.insurance.dto.InsuranceSimulationResponse;
import com.agibank.insurance.repository.InsuranceRepository;
import com.agibank.insurance.service.InsuranceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import com.agibank.insurance.exception.InsuranceException;

@SpringBootTest
@Import(TestConfig.class)
class InsuranceIntegrationTest {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${client.service.url}")
    private String clientServiceUrl;

    @BeforeEach
    void setUp() {
        insuranceRepository.deleteAll();
    }

    @Test
    void shouldSimulateInsuranceForYoungClient() {
        // Given
        InsuranceSimulationRequest request = new InsuranceSimulationRequest();
        request.cpf = "12345678901";
        request.age = 25;

        // When
        InsuranceSimulationResponse response = insuranceService.simulate(request);

        // Then
        assertEquals("Bronze", response.getType());
        assertEquals(50.0, response.getMonthlyFee());
        assertEquals(100000.0, response.getInsuredAmount());
        assertTrue(response.getBenefits().contains("Funeral assistance"));
        assertTrue(response.getBenefits().contains("Accidental death coverage"));
    }

    @Test
    void shouldContractInsuranceWhenClientExists() {
        assertThrows(InsuranceException.class, () -> {
            insuranceService.contractInsurance("12345678901", "Silver");
        });
    }

    @Test
    void shouldUseFallbackWhenClientServiceIsUnavailable() {
        assertThrows(InsuranceException.class, () -> {
            insuranceService.contractInsurance("12345678901", "Silver");
        });
    }

    @Test
    void shouldThrowExceptionForInvalidPlanType() {
        assertThrows(InsuranceException.class, () -> {
            insuranceService.contractInsurance("12345678901", "InvalidType");
        });
    }
} 