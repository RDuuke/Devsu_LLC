package com.devsu.cliente.domain.event;

import lombok.Builder;

@Builder
public record ClienteEvent(
        String eventId,
        ClienteEventType type,
        Long clienteId,
        String nombre,
        boolean estado
) {
}
