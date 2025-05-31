package com.agibank.insurance.dto;

import com.agibank.insurance.model.InsuranceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class InsurancePlanResponse {
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("monthlyFee")
    private double monthlyFee;
    
    @JsonProperty("insuredAmount")
    private double insuredAmount;
    
    @JsonProperty("benefits")
    private List<String> benefits;
    
    @JsonProperty("ageRange")
    private String ageRange;

    public static InsurancePlanResponse fromType(InsuranceType type) {
        InsurancePlanResponse response = new InsurancePlanResponse();
        response.setType(type.name());
        response.setMonthlyFee(type.getMonthlyFee());
        response.setInsuredAmount(type.getInsuredAmount());
        response.setBenefits(type.getBenefits());
        
        // Set age range based on type
        switch (type) {
            case BRONZE:
                response.setAgeRange("Under 30 years");
                break;
            case SILVER:
                response.setAgeRange("30 to 59 years");
                break;
            case GOLD:
                response.setAgeRange("60 years or older");
                break;
            default:
                throw new IllegalArgumentException("Invalid insurance type: " + type);
        }
        
        return response;
    }
} 