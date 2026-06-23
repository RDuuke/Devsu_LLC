package com.devsu.cuenta.presentation.mapper;

import com.devsu.cuenta.application.command.RegistrarMovimientoCommand;
import com.devsu.cuenta.domain.model.Movimiento;
import com.devsu.cuenta.presentation.dto.MovimientoRequest;
import com.devsu.cuenta.presentation.dto.MovimientoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovimientoApiMapper {
    RegistrarMovimientoCommand toCommand(MovimientoRequest request);

    MovimientoResponse toResponse(Movimiento movimiento);
}
