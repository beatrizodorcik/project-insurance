package com.agibank.insurance.model.plan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GoldPlanTest {

    private final GoldPlan goldPlan = new GoldPlan();

    @Test
    void shouldBeEligibleForAge60OrOver() {
        assertTrue(goldPlan.isEligible(60));
        assertTrue(goldPlan.isEligible(70));
        assertTrue(goldPlan.isEligible(80));
    }

    @Test
    void shouldNotBeEligibleForAgeUnder60() {
        assertFalse(goldPlan.isEligible(59));
        assertFalse(goldPlan.isEligible(45));
        assertFalse(goldPlan.isEligible(30));
    }

    @Test
    void shouldHaveCorrectPlanDetails() {
        assertEquals("Gold", goldPlan.getType());
        assertEquals(200.0, goldPlan.getMonthlyFee());
        assertEquals(200000.0, goldPlan.getInsuredAmount());
        assertTrue(goldPlan.getBenefits().contains("Full life coverage"));
        assertTrue(goldPlan.getBenefits().contains("Hospital assistance"));
        assertTrue(goldPlan.getBenefits().contains("Funeral"));
        assertTrue(goldPlan.getBenefits().contains("Disability"));
        assertEquals(4, goldPlan.getBenefits().size());
    }
} 