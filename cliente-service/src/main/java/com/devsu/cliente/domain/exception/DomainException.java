package com.devsu.cliente.domain.exception;

/** Excepción base del dominio. */
public abstract class DomainException extends RuntimeException {
    protected DomainException(String message) {
        super(message);
    }
}
