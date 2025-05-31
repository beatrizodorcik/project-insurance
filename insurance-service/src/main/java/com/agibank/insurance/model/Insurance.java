package com.agibank.insurance.model;

import com.agibank.insurance.model.plan.InsurancePlan;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "insurances")
public class Insurance {
    @Id
    private String id;
    private String cpf;
    private String type;
    private double monthlyFee;
    private double insuredAmount;
    private List<String> benefits;

    public Insurance(String cpf, InsurancePlan plan) {
        this.cpf = cpf;
        this.type = InsuranceType.valueOf(plan.getType().toUpperCase()).name();
        this.monthlyFee = plan.getMonthlyFee();
        this.insuredAmount = plan.getInsuredAmount();
        this.benefits = plan.getBenefits();
    }

    public Insurance(String cpf, String type, double monthlyFee, double insuredAmount, List<String> benefits) {
        this.cpf = cpf;
        this.type = type;
        this.monthlyFee = monthlyFee;
        this.insuredAmount = insuredAmount;
        this.benefits = benefits;
    }
}