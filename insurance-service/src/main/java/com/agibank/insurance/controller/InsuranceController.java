package com.agibank.insurance.controller;

import com.agibank.insurance.dto.InsurancePlanResponse;
import com.agibank.insurance.dto.InsuranceSimulationRequest;
import com.agibank.insurance.dto.InsuranceSimulationResponse;
import com.agibank.insurance.model.Insurance;
import com.agibank.insurance.model.InsuranceType;
import com.agibank.insurance.service.InsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/insurances")
@Tag(name = "Insurance API", description = "API for managing insurance contracts")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping("/plans")
    @Operation(summary = "List all available insurance plans")
    public ResponseEntity<List<InsurancePlanResponse>> listPlans() {
        List<InsurancePlanResponse> plans = List.of(InsuranceType.values())
            .stream()
            .map(InsurancePlanResponse::fromType)
            .collect(Collectors.toList());
        return ResponseEntity.ok(plans);
    }

    @PostMapping("/simulate")
    @Operation(summary = "Simulate insurance quotes")
    public ResponseEntity<InsuranceSimulationResponse> simulate(@Valid @RequestBody InsuranceSimulationRequest request) {
        return ResponseEntity.ok(insuranceService.simulate(request));
    }

    @PostMapping("/contract")
    @Operation(summary = "Contract insurance")
    public ResponseEntity<Insurance> contract(
            @RequestParam @Pattern(regexp = "^\\d{11}$", message = "CPF must contain exactly 11 digits") String cpf,
            @RequestParam @Pattern(regexp = "^(Bronze|Silver|Gold)$", message = "Insurance type must be Bronze, Silver, or Gold") String type) {
        return ResponseEntity.ok(insuranceService.contractInsurance(cpf, type));
    }
}