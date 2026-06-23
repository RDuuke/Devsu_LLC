package com.devsu.cliente.infrastructure.messaging.outbox;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

/**
 * Publica periódicamente los eventos pendientes del Outbox a RabbitMQ. Si el broker
 * está caído quedan en la tabla y se reintentan en el siguiente ciclo. Cada mensaje
 * lleva {@code messageId = eventId} para que el consumidor deduplique.
 */
@Component
@RequiredArgsConstructor
public class OutboxRelay {

    private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);
    private static final int BATCH_SIZE = 100;

    private final OutboxJpaRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelayString = "${app.outbox.poll-interval-ms:2000}")
    @Transactional
    public void publishPending() {
        List<OutboxEvent> pending =
                outboxRepository.findByPublishedAtIsNullOrderByCreatedAtAsc(Limit.of(BATCH_SIZE));
        if (pending.isEmpty()) {
            return;
        }
        for (OutboxEvent event : pending) {
            try {
                org.springframework.amqp.core.Message message = MessageBuilder
                        .withBody(event.getPayload().getBytes(StandardCharsets.UTF_8))
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .setMessageId(event.getEventId())
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                        .setHeader("eventType", event.getType())
                        .build();

                rabbitTemplate.send(
                        com.devsu.cliente.infrastructure.messaging.RabbitConfig.EXCHANGE,
                        event.getRoutingKey(),
                        message);

                event.markPublished(Instant.now());
            } catch (Exception ex) {
                // Broker caído: lo dejamos pendiente y se reintenta luego.
                log.warn("No se pudo publicar el evento {}: {}. Se reintentará.",
                        event.getEventId(), ex.getMessage());
            }
        }
        outboxRepository.saveAll(pending);
        log.debug("Relay procesó {} eventos del outbox", pending.size());
    }
}
