package com.devsu.cuenta.infrastructure.messaging;

import com.devsu.cuenta.application.ClienteProjectionService;
import com.devsu.cuenta.infrastructure.messaging.idempotency.ProcessedEvent;
import com.devsu.cuenta.infrastructure.messaging.idempotency.ProcessedEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ClienteEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ClienteEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final ClienteProjectionService projectionService;
    private final ProcessedEventRepository processedEventRepository;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    @Transactional
    public void onMessage(Message message) throws IOException {
        ClienteEventMessage event = objectMapper.readValue(message.getBody(), ClienteEventMessage.class);

        String messageId = message.getMessageProperties().getMessageId();
        String eventId = (messageId != null) ? messageId : event.eventId();

        if (eventId != null && processedEventRepository.existsById(eventId)) {
            log.debug("Evento {} ya procesado, se ignora (idempotencia)", eventId);
            return;
        }

        switch (event.type()) {
            case "CLIENTE_CREADO", "CLIENTE_ACTUALIZADO" ->
                    projectionService.aplicarUpsert(event.clienteId(), event.nombre(), event.estado());
            case "CLIENTE_ELIMINADO" ->
                    projectionService.aplicarEliminacion(event.clienteId());
            default -> log.warn("Tipo de evento desconocido: {}", event.type());
        }

        if (eventId != null) {
            processedEventRepository.save(new ProcessedEvent(eventId, Instant.now()));
        }
        log.info("Proyección de cliente actualizada por evento {} ({})", eventId, event.type());
    }
}
