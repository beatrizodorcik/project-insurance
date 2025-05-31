package com.agibank.insurance.model.plan;

import lombok.Getter;
import java.util.List;

@Getter
public abstract class InsurancePlan {
    protected final String type;
    protected final double monthlyFee;
    protected final double insuredAmount;
    protected final List<String> benefits;

    protected InsurancePlan(String type, double monthlyFee, double insuredAmount, List<String> benefits) {
        this.type = type;
        this.monthlyFee = monthlyFee;
        this.insuredAmount = insuredAmount;
        this.benefits = benefits;
    }

    public abstract boolean isEligible(int age);
} 