package com.devsu.cuenta.domain.exception;

public class CuentaInactivaException extends DomainException {
    public CuentaInactivaException(String numeroCuenta) {
        super("La cuenta " + numeroCuenta + " está inactiva");
    }
}
