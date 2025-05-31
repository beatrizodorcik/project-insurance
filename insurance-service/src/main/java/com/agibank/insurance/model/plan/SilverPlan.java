package com.agibank.insurance.model.plan;

import java.util.Arrays;
import java.util.List;

public class SilverPlan extends InsurancePlan {
    private static final List<String> BENEFITS = Arrays.asList(
        "Funeral assistance",
        "Accidental death",
        "Permanent disability"
    );

    public SilverPlan() {
        super("Silver", 100.0, 150000.0, BENEFITS);
    }

    @Override
    public boolean isEligible(int age) {
        return age >= 30 && age < 60;
    }
} 