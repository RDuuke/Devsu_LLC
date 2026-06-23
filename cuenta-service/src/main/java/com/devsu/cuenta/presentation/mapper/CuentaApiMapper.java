package com.devsu.cuenta.presentation.mapper;

import com.devsu.cuenta.application.command.ActualizarCuentaCommand;
import com.devsu.cuenta.application.command.CrearCuentaCommand;
import com.devsu.cuenta.domain.model.Cuenta;
import com.devsu.cuenta.presentation.dto.CuentaRequest;
import com.devsu.cuenta.presentation.dto.CuentaResponse;
import com.devsu.cuenta.presentation.dto.CuentaUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaApiMapper {
    CrearCuentaCommand toCommand(CuentaRequest request);

    ActualizarCuentaCommand toCommand(CuentaUpdateRequest request);

    CuentaResponse toResponse(Cuenta cuenta);
}
