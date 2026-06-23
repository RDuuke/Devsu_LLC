package com.devsu.cuenta.domain.exception;

public class DuplicateCuentaException extends DomainException {
    public DuplicateCuentaException(String numeroCuenta) {
        super("Ya existe una cuenta con número " + numeroCuenta);
    }
}
