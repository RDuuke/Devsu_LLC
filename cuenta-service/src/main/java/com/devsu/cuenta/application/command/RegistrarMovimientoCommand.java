package com.devsu.cuenta.application.command;

import com.devsu.cuenta.domain.model.TipoMovimiento;

import java.math.BigDecimal;

public record RegistrarMovimientoCommand(
        Long cuentaId,
        TipoMovimiento tipoMovimiento,
        BigDecimal valor
) {
}
