package com.example.event_management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Event Management API",
                version = "v1",
                description = "Production-ready API for managing university events with validation, pagination and structured error handling.",
                contact = @Contact(
                        name = "Fariza",
                        email = "fariza@example.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Local development server")
        }
)
public class OpenApiConfig {
}
