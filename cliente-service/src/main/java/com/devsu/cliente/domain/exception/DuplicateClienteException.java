package com.devsu.cliente.domain.exception;

public class DuplicateClienteException extends DomainException {
    public DuplicateClienteException(String campo, String valor) {
        super("Ya existe un cliente con " + campo + " = " + valor);
    }
}
