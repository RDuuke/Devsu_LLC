package com.devsu.cliente.domain.exception;

/** Se lanza al intentar crear un cliente con una identificación o clienteId ya existente. */
public class DuplicateClienteException extends DomainException {
    public DuplicateClienteException(String campo, String valor) {
        super("Ya existe un cliente con " + campo + " = " + valor);
    }
}
