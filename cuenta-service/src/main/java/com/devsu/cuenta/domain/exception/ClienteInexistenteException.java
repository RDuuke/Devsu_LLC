package com.devsu.cuenta.domain.exception;

/** Cliente ausente en la proyección local al crear una cuenta. */
public class ClienteInexistenteException extends DomainException {
    public ClienteInexistenteException(Long clienteId) {
        super("Cliente no existe: " + clienteId);
    }
}
