package com.devsu.cliente.infrastructure.persistence.jpa;

import com.devsu.cliente.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    boolean existsByIdentificacion(String identificacion);

    boolean existsByClienteId(String clienteId);
}
