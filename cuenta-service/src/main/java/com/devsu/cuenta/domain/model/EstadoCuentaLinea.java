package com.devsu.cuenta.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Una línea por movimiento en el reporte de estado de cuenta. */
@Builder
public record EstadoCuentaLinea(
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
