package com.devsu.cliente.presentation.dto;

import com.devsu.cliente.domain.model.Genero;

/** DTO de salida. No expone la contraseña ni su hash. */
public record ClienteResponse(
        Long id,
        String nombre,
        Genero genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        String clienteId,
        boolean estado
) {
}
