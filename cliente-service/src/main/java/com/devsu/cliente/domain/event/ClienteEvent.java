package com.devsu.cliente.domain.event;

import lombok.Builder;

/** Evento que viaja a cuenta-service. Solo los datos que el consumidor necesita. */
@Builder
public record ClienteEvent(
        String eventId,
        ClienteEventType type,
        Long clienteId,
        String nombre,
        boolean estado
) {
}
