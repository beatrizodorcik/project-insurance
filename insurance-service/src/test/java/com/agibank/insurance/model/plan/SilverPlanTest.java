package com.agibank.insurance.model.plan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SilverPlanTest {

    private final SilverPlan silverPlan = new SilverPlan();

    @Test
    void shouldBeEligibleForAgeBetween30And59() {
        assertTrue(silverPlan.isEligible(30));
        assertTrue(silverPlan.isEligible(45));
        assertTrue(silverPlan.isEligible(59));
    }

    @Test
    void shouldNotBeEligibleForAgeUnder30OrOver60() {
        assertFalse(silverPlan.isEligible(29));
        assertFalse(silverPlan.isEligible(60));
        assertFalse(silverPlan.isEligible(70));
    }

    @Test
    void shouldHaveCorrectPlanDetails() {
        assertEquals("Silver", silverPlan.getType());
        assertEquals(100.0, silverPlan.getMonthlyFee());
        assertEquals(150000.0, silverPlan.getInsuredAmount());
        assertTrue(silverPlan.getBenefits().contains("Funeral assistance"));
        assertTrue(silverPlan.getBenefits().contains("Accidental death"));
        assertTrue(silverPlan.getBenefits().contains("Permanent disability"));
        assertEquals(3, silverPlan.getBenefits().size());
    }
} 