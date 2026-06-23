package com.devsu.cliente.infrastructure.messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Declara el topic exchange donde el relay publica los eventos de cliente. */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "cliente.exchange";
    public static final String ROUTING_KEY = "cliente.event";

    @Bean
    public TopicExchange clienteExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }
}
