package com.devsu.cliente.application.command;

import com.devsu.cliente.domain.model.Genero;

public record ActualizarClienteCommand(
        String nombre,
        Genero genero,
        Integer edad,
        String direccion,
        String telefono,
        String contrasena,
        Boolean estado
) {
}
