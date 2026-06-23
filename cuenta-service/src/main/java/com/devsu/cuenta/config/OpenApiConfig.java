package com.devsu.cuenta.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cuentaOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Cuenta Service API")
                .version("1.0.0")
                .description("Microservicio de Cuentas, Movimientos y Reportes (Prueba Técnica Devsu)"));
    }
}
