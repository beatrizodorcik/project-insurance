package com.agibank.insurance.model.plan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InsurancePlanFactoryTest {

    private final InsurancePlanFactory factory = new InsurancePlanFactory();

    @Test
    void shouldReturnBronzePlanForYoungClient() {
        InsurancePlan plan = factory.getPlanForAge(25);
        assertTrue(plan instanceof BronzePlan);
        assertEquals("Bronze", plan.getType());
    }

    @Test
    void shouldReturnSilverPlanForMiddleAgedClient() {
        InsurancePlan plan = factory.getPlanForAge(45);
        assertTrue(plan instanceof SilverPlan);
        assertEquals("Silver", plan.getType());
    }

    @Test
    void shouldReturnGoldPlanForSeniorClient() {
        InsurancePlan plan = factory.getPlanForAge(65);
        assertTrue(plan instanceof GoldPlan);
        assertEquals("Gold", plan.getType());
    }

    @Test
    void shouldThrowExceptionForInvalidAge() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getPlanForAge(-1);
        });
        assertEquals("Age cannot be negative: -1", exception.getMessage());
    }

    @Test
    void shouldGetPlanByType() {
        assertTrue(factory.getPlanByType("Bronze") instanceof BronzePlan);
        assertTrue(factory.getPlanByType("Silver") instanceof SilverPlan);
        assertTrue(factory.getPlanByType("Gold") instanceof GoldPlan);
    }

    @Test
    void shouldThrowExceptionForInvalidPlanType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getPlanByType("InvalidType");
        });
        assertEquals("Invalid plan type: InvalidType", exception.getMessage());
    }
} 