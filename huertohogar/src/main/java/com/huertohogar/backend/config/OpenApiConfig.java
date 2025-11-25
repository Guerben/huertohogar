package com.huertohogar.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("HuertoHogar API")
                        .version("1.0.0")
                        .description("API para gestionar HuertoHogar backend")
                        .contact(new Contact().name("Equipo HuertoHogar").email("dev@huertohogar.local")));
    }
}
