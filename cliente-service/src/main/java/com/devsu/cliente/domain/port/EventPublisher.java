package com.devsu.cliente.domain.port;

import com.devsu.cliente.domain.event.ClienteEvent;

/** Puerto para publicar eventos. La implementación usa Outbox para garantizar la entrega. */
public interface EventPublisher {
    void publish(ClienteEvent event);
}
