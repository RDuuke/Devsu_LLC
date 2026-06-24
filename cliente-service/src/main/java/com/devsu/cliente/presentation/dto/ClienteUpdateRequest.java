package com.devsu.cliente.presentation.dto;

import com.devsu.cliente.domain.model.Genero;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ClienteUpdateRequest(
        String nombre,
        Genero genero,
        @Min(0) @Max(150) Integer edad,
        String direccion,
        String telefono,
        @Size(min = 4, max = 100) String contrasena,
        Boolean estado
) {
}
