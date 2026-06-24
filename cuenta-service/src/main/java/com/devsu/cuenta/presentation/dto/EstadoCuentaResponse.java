package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoCuenta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstadoCuentaResponse(
        LocalDate fecha,
        String cliente,
        String numeroCuenta,
        TipoCuenta tipo,
        BigDecimal saldoInicial,
        boolean estado,
        BigDecimal movimiento,
        BigDecimal saldoDisponible
) {
}
