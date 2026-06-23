package com.devsu.cliente.application.command;

import com.devsu.cliente.domain.model.Genero;

public record CrearClienteCommand(
        String nombre,
        Genero genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        String clienteId,
        String contrasena,
        Boolean estado
) {
}
