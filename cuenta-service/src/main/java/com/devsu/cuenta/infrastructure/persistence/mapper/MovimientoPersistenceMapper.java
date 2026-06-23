package com.devsu.cuenta.infrastructure.persistence.mapper;

import com.devsu.cuenta.domain.model.Movimiento;
import com.devsu.cuenta.infrastructure.persistence.entity.MovimientoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovimientoPersistenceMapper {
    MovimientoEntity toEntity(Movimiento movimiento);

    Movimiento toDomain(MovimientoEntity entity);
}
