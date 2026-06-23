package com.devsu.cuenta.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Topología RabbitMQ: exchange de cliente, cola principal y DLQ para fallos. */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "cliente.exchange";
    public static final String ROUTING_KEY = "cliente.event";
    public static final String QUEUE = "cuenta.cliente.queue";

    public static final String DLX = "cuenta.cliente.dlx";
    public static final String DLQ = "cuenta.cliente.dlq";

    @Bean
    public TopicExchange clienteExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue clienteQueue() {
        return QueueBuilder.durable(QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .build();
    }

    @Bean
    public Binding clienteBinding() {
        return BindingBuilder.bind(clienteQueue()).to(clienteExchange()).with(ROUTING_KEY);
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX, true, false);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }
}
