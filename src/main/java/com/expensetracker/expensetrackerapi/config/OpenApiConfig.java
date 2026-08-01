package com.expensetracker.expensetrackerapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI expenseTrackerAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Smart Expense Tracker API")

                        .version("1.0.0")

                        .description("REST API for managing personal expenses using Spring Boot.")

                        .contact(new Contact()
                                .name("Akash Sinha")
                                .email("akash.sinha@example.com"))

                        .license(new License()
                                .name("MIT License")))

                .externalDocs(new ExternalDocumentation()
                        .description("Expense Tracker API Documentation"));
    }
}