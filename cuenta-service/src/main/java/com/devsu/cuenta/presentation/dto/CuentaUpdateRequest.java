package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoCuenta;

/** Campos nulos = sin cambio. */
public record CuentaUpdateRequest(
        TipoCuenta tipoCuenta,
        Boolean estado
) {
}
