package com.example.onlineshopping;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
@OpenAPIDefinition
public class AppConfiguration {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
