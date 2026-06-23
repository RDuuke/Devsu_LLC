package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MovimientoUpdateRequest(
        @NotNull TipoMovimiento tipoMovimiento,
        @NotNull @Positive BigDecimal valor
) {
}
