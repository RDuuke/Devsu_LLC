package com.devsu.cuenta.infrastructure.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Evento recibido desde cliente-service. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ClienteEventMessage(
        String eventId,
        String type,
        Long clienteId,
        String nombre,
        boolean estado
) {
}
