package com.sms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger Configuration.
 * Provides API documentation and metadata.
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Student Management System API")
                .description("REST API for comprehensive student management system\n\n" +
                    "## Features\n" +
                    "- Full CRUD operations\n" +
                    "- Advanced search and filtering\n" +
                    "- Pagination support\n" +
                    "- System statistics\n" +
                    "- Multiple database support (MySQL, PostgreSQL)\n\n" +
                    "## Authentication\n" +
                    "All endpoints require authentication. Default credentials:\n" +
                    "- Username: admin\n" +
                    "- Password: admin123\n\n" +
                    "## Base URL\n" +
                    "`http://localhost:8080/api/v1`")
                .version("2.0.0")
                .contact(new Contact()
                    .name("SMS Development Team")
                    .email("dev@sms.local")
                    .url("https://github.com/your-org/StudentManagementSystem"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Local development server"),
                new Server()
                    .url("https://api.example.com")
                    .description("Production server")
            ));
    }
}
