package com.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Application - Main entry point for REST API and Web UI.
 * 
 * Features:
 * - REST API endpoints for CRUD operations
 * - Web UI with Thymeleaf
 * - Authentication & Authorization
 * - MySQL/PostgreSQL database support
 * - API Documentation (Swagger/OpenAPI)
 * 
 * @author SMS Development Team
 * @version 2.0
 * @since 2024
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.sms"})
public class SpringBootApp {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
