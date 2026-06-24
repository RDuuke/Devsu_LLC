package com.devsu.cliente.infrastructure.messaging.outbox;

import com.devsu.cliente.domain.event.ClienteEvent;
import com.devsu.cliente.domain.port.EventPublisher;
import com.devsu.cliente.infrastructure.messaging.RabbitConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher implements EventPublisher {

    private final OutboxJpaRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(ClienteEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            OutboxEvent record = new OutboxEvent(
                    event.eventId(),
                    "Cliente",
                    event.type().name(),
                    RabbitConfig.ROUTING_KEY,
                    payload,
                    Instant.now()
            );
            outboxRepository.save(record);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("No se pudo serializar el evento de cliente", e);
        }
    }
}
