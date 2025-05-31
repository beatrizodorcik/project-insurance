package com.agibank.insurance.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

@Getter
public enum InsuranceType {
    BRONZE(50.0, 100000.0, Arrays.asList("Funeral assistance", "Accidental death coverage")),
    SILVER(100.0, 150000.0, Arrays.asList("Funeral assistance", "Accidental death", "Permanent disability")),
    GOLD(200.0, 200000.0, Arrays.asList("Full life coverage", "Hospital assistance", "Funeral", "Disability"));

    private final double monthlyFee;
    private final double insuredAmount;
    private final List<String> benefits;

    InsuranceType(double monthlyFee, double insuredAmount, List<String> benefits) {
        this.monthlyFee = monthlyFee;
        this.insuredAmount = insuredAmount;
        this.benefits = benefits;
    }

    @JsonValue
    public String getValue() {
        return name();
    }

    public static InsuranceType fromAge(int age) {
        if (age < 30) {
            return BRONZE;
        } else if (age < 60) {
            return SILVER;
        } else {
            return GOLD;
        }
    }
} 