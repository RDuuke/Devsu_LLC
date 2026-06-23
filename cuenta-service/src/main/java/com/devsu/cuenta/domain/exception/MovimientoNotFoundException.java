package com.devsu.cuenta.domain.exception;

public class MovimientoNotFoundException extends DomainException {
    public MovimientoNotFoundException(Long id) {
        super("Movimiento no encontrado: " + id);
    }
}
