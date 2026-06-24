package com.devsu.cuenta.domain.exception;

public class SaldoInsuficienteException extends DomainException {
    public SaldoInsuficienteException() {
        super("Saldo no disponible");
    }
}
