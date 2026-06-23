package com.devsu.cuenta.config;

import com.devsu.cuenta.infrastructure.security.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Registra el filtro de API Key para todas las rutas (el filtro decide qué exigir). */
@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilter(
            @Value("${app.security.api-key}") String apiKey) {
        FilterRegistrationBean<ApiKeyFilter> registration = new FilterRegistrationBean<>(new ApiKeyFilter(apiKey));
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
