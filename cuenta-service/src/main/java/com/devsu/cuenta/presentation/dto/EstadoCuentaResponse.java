package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoCuenta;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Reporte de estado de cuenta (F4). */
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
