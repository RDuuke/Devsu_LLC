package com.devsu.cuenta.presentation.mapper;

import com.devsu.cuenta.domain.model.EstadoCuentaLinea;
import com.devsu.cuenta.presentation.dto.EstadoCuentaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReporteApiMapper {
    EstadoCuentaResponse toResponse(EstadoCuentaLinea linea);
}
