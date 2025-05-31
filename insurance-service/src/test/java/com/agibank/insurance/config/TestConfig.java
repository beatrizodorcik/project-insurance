package com.agibank.insurance.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate;

@TestConfiguration
@ComponentScan(excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = RestTemplateConfig.class
))
public class TestConfig {

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new TestRestTemplate().getRestTemplate();
    }
} 