package com.agibank.insurance.model.plan;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class InsurancePlanFactory {
    private final List<InsurancePlan> availablePlans;

    public InsurancePlanFactory() {
        this.availablePlans = List.of(
            new BronzePlan(),
            new SilverPlan(),
            new GoldPlan()
        );
    }

    public InsurancePlan getPlanForAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        return availablePlans.stream()
            .filter(plan -> plan.isEligible(age))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No suitable plan found for age: " + age));
    }

    public InsurancePlan getPlanByType(String type) {
        return availablePlans.stream()
            .filter(plan -> plan.getType().equalsIgnoreCase(type))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid plan type: " + type));
    }
} 