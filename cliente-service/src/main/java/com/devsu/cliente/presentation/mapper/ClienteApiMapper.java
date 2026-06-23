package com.devsu.cliente.presentation.mapper;

import com.devsu.cliente.application.command.ActualizarClienteCommand;
import com.devsu.cliente.application.command.CrearClienteCommand;
import com.devsu.cliente.domain.model.Cliente;
import com.devsu.cliente.presentation.dto.ClienteRequest;
import com.devsu.cliente.presentation.dto.ClienteResponse;
import com.devsu.cliente.presentation.dto.ClienteUpdateRequest;
import org.mapstruct.Mapper;

/** Mapea entre DTOs de la API y comandos/dominio. */
@Mapper(componentModel = "spring")
public interface ClienteApiMapper {

    CrearClienteCommand toCommand(ClienteRequest request);

    ActualizarClienteCommand toCommand(ClienteUpdateRequest request);

    ClienteResponse toResponse(Cliente cliente);
}
