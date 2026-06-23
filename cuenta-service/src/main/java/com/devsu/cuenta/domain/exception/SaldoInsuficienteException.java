package com.devsu.cuenta.domain.exception;

/** El mensaje "Saldo no disponible" es exacto y se mapea a HTTP 422. */
public class SaldoInsuficienteException extends DomainException {
    public SaldoInsuficienteException() {
        super("Saldo no disponible");
    }
}
