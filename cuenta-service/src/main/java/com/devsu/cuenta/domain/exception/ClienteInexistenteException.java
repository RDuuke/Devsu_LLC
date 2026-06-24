package com.devsu.cuenta.domain.exception;

public class ClienteInexistenteException extends DomainException {
    public ClienteInexistenteException(Long clienteId) {
        super("Cliente no existe: " + clienteId);
    }
}
