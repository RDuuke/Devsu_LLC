package com.devsu.cliente.domain.port;

import com.devsu.cliente.domain.event.ClienteEvent;

public interface EventPublisher {
    void publish(ClienteEvent event);
}
