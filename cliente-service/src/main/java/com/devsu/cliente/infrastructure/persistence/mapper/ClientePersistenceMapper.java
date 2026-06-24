package com.devsu.cliente.infrastructure.persistence.mapper;

import com.devsu.cliente.domain.model.Cliente;
import com.devsu.cliente.infrastructure.persistence.entity.ClienteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientePersistenceMapper {

    ClienteEntity toEntity(Cliente cliente);

    Cliente toDomain(ClienteEntity entity);
}
