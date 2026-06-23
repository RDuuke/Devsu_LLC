package com.devsu.cuenta.application.command;

import com.devsu.cuenta.domain.model.TipoMovimiento;

import java.math.BigDecimal;

/** El valor es la magnitud positiva; la dirección la marca el tipo. */
public record RegistrarMovimientoCommand(
        Long cuentaId,
        TipoMovimiento tipoMovimiento,
        BigDecimal valor
) {
}
