package com.example.onlineshopping.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
@OpenAPIDefinition(info = @Info(title = "swagger",
        version = "1.0",
        description = "document"
))
public class AppConfiguration {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
