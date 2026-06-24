package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoCuenta;

public record CuentaUpdateRequest(
        TipoCuenta tipoCuenta,
        Boolean estado
) {
}
