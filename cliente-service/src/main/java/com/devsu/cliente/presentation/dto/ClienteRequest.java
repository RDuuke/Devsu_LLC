package com.devsu.cliente.presentation.dto;

import com.devsu.cliente.domain.model.Genero;
import jakarta.validation.constraints.*;

/** DTO de entrada para crear un cliente. */
public record ClienteRequest(
        @NotBlank String nombre,
        @NotNull Genero genero,
        @NotNull @Min(0) @Max(150) Integer edad,
        @NotBlank String identificacion,
        String direccion,
        String telefono,
        @NotBlank String clienteId,
        @NotBlank @Size(min = 4, max = 100) String contrasena,
        Boolean estado
) {
}
