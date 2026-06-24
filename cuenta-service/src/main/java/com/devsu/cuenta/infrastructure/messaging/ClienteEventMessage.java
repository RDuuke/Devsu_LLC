package com.devsu.cuenta.infrastructure.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClienteEventMessage(
        String eventId,
        String type,
        Long clienteId,
        String nombre,
        boolean estado
) {
}
