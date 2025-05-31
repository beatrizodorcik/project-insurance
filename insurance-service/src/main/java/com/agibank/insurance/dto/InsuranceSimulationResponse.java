package com.agibank.insurance.dto;

import com.agibank.insurance.model.plan.InsurancePlan;
import lombok.Data;
import java.util.List;

@Data
public class InsuranceSimulationResponse {
    private String type;
    private double monthlyFee;
    private double insuredAmount;
    private List<String> benefits;

    public static InsuranceSimulationResponse fromPlan(InsurancePlan plan) {
        InsuranceSimulationResponse response = new InsuranceSimulationResponse();
        response.setType(plan.getType());
        response.setMonthlyFee(plan.getMonthlyFee());
        response.setInsuredAmount(plan.getInsuredAmount());
        response.setBenefits(plan.getBenefits());
        return response;
    }
}