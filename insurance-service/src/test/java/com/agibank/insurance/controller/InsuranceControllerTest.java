package com.agibank.insurance.controller;

import com.agibank.insurance.dto.InsuranceSimulationRequest;
import com.agibank.insurance.dto.InsuranceSimulationResponse;
import com.agibank.insurance.model.Insurance;
import com.agibank.insurance.service.InsuranceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InsuranceController.class)
class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceService service;

    @Autowired
    private ObjectMapper objectMapper;

    private InsuranceSimulationRequest simulationRequest;
    private InsuranceSimulationResponse simulationResponse;
    private Insurance insurance = new Insurance("12345678901", "Silver", 100.0, 150000.0, List.of("Funeral assistance", "Accidental death", "Permanent disability"));

    @BeforeEach
    void setUp() {
        simulationRequest = new InsuranceSimulationRequest();
        simulationRequest.cpf = "12345678901";
        simulationRequest.age = 30;

        simulationResponse = new InsuranceSimulationResponse();
        simulationResponse.setType("Silver");
        simulationResponse.setMonthlyFee(100.0);
        simulationResponse.setInsuredAmount(30000.0);
        simulationResponse.setBenefits(Arrays.asList("Funeral assistance", "Accidental death", "Permanent disability"));
    }

    @Test
    void testSimulateInsurance() throws Exception {
        Mockito.when(service.simulate(any(InsuranceSimulationRequest.class))).thenReturn(simulationResponse);
        mockMvc.perform(post("/api/insurances/simulate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(simulationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Silver"));
    }

    @Test
    void testContractInsurance() throws Exception {
        Mockito.when(service.contractInsurance(eq("12345678901"), eq("Silver"))).thenReturn(insurance);
        mockMvc.perform(post("/api/insurances/contract")
                .param("cpf", "12345678901")
                .param("type", "Silver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.type").value("Silver"));
    }

    @Test
    public void listPlans_ShouldReturnAllPlans() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/insurances/plans"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].type").value("BRONZE"))
            .andExpect(jsonPath("$[0].monthlyFee").value(50.0))
            .andExpect(jsonPath("$[0].insuredAmount").value(100000.0))
            .andExpect(jsonPath("$[0].ageRange").value("Under 30 years"))
            .andExpect(jsonPath("$[0].benefits").isArray())
            .andExpect(jsonPath("$[0].benefits[0]").value("Funeral assistance"))
            .andExpect(jsonPath("$[0].benefits[1]").value("Accidental death coverage"))

            .andExpect(jsonPath("$[1].type").value("SILVER"))
            .andExpect(jsonPath("$[1].monthlyFee").value(100.0))
            .andExpect(jsonPath("$[1].insuredAmount").value(150000.0))
            .andExpect(jsonPath("$[1].ageRange").value("30 to 59 years"))
            .andExpect(jsonPath("$[1].benefits").isArray())
            .andExpect(jsonPath("$[1].benefits[0]").value("Funeral assistance"))
            .andExpect(jsonPath("$[1].benefits[1]").value("Accidental death"))
            .andExpect(jsonPath("$[1].benefits[2]").value("Permanent disability"))

            .andExpect(jsonPath("$[2].type").value("GOLD"))
            .andExpect(jsonPath("$[2].monthlyFee").value(200.0))
            .andExpect(jsonPath("$[2].insuredAmount").value(200000.0))
            .andExpect(jsonPath("$[2].ageRange").value("60 years or older"))
            .andExpect(jsonPath("$[2].benefits").isArray())
            .andExpect(jsonPath("$[2].benefits[0]").value("Full life coverage"))
            .andExpect(jsonPath("$[2].benefits[1]").value("Hospital assistance"))
            .andExpect(jsonPath("$[2].benefits[2]").value("Funeral"))
            .andExpect(jsonPath("$[2].benefits[3]").value("Disability"));
    }
} 