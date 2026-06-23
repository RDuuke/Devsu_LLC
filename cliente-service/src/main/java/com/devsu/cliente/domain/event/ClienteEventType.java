package com.devsu.cliente.domain.event;

/** Tipos de evento de dominio publicados hacia otros microservicios. */
public enum ClienteEventType {
    CLIENTE_CREADO,
    CLIENTE_ACTUALIZADO,
    CLIENTE_ELIMINADO
}
