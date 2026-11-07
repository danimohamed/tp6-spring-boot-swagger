package com.example.studentmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentManagementOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Student Management API")
                .description("API REST de gestion des etudiants - TP 6 Spring Boot et Swagger")
                .version("1.0.0")
                .contact(new Contact().name("Mohamed Dani")));
    }
}
