package com.devsu.cuenta.application.command;

import com.devsu.cuenta.domain.model.TipoCuenta;

import java.math.BigDecimal;

public record CrearCuentaCommand(
        String numeroCuenta,
        TipoCuenta tipoCuenta,
        BigDecimal saldoInicial,
        Boolean estado,
        Long clienteId
) {
}
