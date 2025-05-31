package com.agibank.insurance.model.plan;

import java.util.Arrays;
import java.util.List;

public class BronzePlan extends InsurancePlan {
    private static final List<String> BENEFITS = Arrays.asList(
        "Funeral assistance",
        "Accidental death coverage"
    );

    public BronzePlan() {
        super("Bronze", 50.0, 100000.0, BENEFITS);
    }

    @Override
    public boolean isEligible(int age) {
        return age < 30;
    }
} 