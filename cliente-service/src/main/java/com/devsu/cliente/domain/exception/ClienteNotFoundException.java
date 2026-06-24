package com.devsu.cliente.domain.exception;

public class ClienteNotFoundException extends DomainException {
    public ClienteNotFoundException(Long id) {
        super("Cliente no encontrado: " + id);
    }
}
