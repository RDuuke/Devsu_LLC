package com.devsu.cuenta.infrastructure.persistence.mapper;

import com.devsu.cuenta.domain.model.ClienteView;
import com.devsu.cuenta.infrastructure.persistence.entity.ClienteViewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteViewPersistenceMapper {
    ClienteViewEntity toEntity(ClienteView clienteView);

    ClienteView toDomain(ClienteViewEntity entity);
}
