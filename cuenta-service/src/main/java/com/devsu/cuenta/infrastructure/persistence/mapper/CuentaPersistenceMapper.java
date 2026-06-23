package com.devsu.cuenta.infrastructure.persistence.mapper;

import com.devsu.cuenta.domain.model.Cuenta;
import com.devsu.cuenta.infrastructure.persistence.entity.CuentaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaPersistenceMapper {
    CuentaEntity toEntity(Cuenta cuenta);

    Cuenta toDomain(CuentaEntity entity);
}
