package com.devsu.cuenta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS para permitir que la Swagger UI unificada (servida desde otro puerto)
 * pueda leer el documento OpenAPI de este servicio.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v3/api-docs/**")
                .allowedOrigins("http://localhost:8081", "http://localhost:8082")
                .allowedMethods("GET");
        registry.addMapping("/v3/api-docs")
                .allowedOrigins("http://localhost:8081", "http://localhost:8082")
                .allowedMethods("GET");
    }
}
