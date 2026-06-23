package com.devsu.cliente.domain.exception;

/** Se lanza cuando no existe un cliente con el identificador solicitado. */
public class ClienteNotFoundException extends DomainException {
    public ClienteNotFoundException(Long id) {
        super("Cliente no encontrado: " + id);
    }
}
