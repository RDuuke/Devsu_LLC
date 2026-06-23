package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoCuenta;

import java.math.BigDecimal;

public record CuentaResponse(
        Long id,
        String numeroCuenta,
        TipoCuenta tipoCuenta,
        BigDecimal saldoInicial,
        BigDecimal saldoActual,
        boolean estado,
        Long clienteId
) {
}
