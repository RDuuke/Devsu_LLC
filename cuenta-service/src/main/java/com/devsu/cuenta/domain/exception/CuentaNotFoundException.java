package com.devsu.cuenta.domain.exception;

public class CuentaNotFoundException extends DomainException {
    public CuentaNotFoundException(Long id) {
        super("Cuenta no encontrada: " + id);
    }
}
