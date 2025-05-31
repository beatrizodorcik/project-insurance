package com.agibank.insurance.model.plan;

import java.util.Arrays;
import java.util.List;

public class GoldPlan extends InsurancePlan {
    private static final List<String> BENEFITS = Arrays.asList(
        "Full life coverage",
        "Hospital assistance",
        "Funeral",
        "Disability"
    );

    public GoldPlan() {
        super("Gold", 200.0, 200000.0, BENEFITS);
    }

    @Override
    public boolean isEligible(int age) {
        return age >= 60;
    }
} 