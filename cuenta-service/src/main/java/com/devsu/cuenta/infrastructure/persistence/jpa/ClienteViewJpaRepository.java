package com.devsu.cuenta.infrastructure.persistence.jpa;

import com.devsu.cuenta.infrastructure.persistence.entity.ClienteViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteViewJpaRepository extends JpaRepository<ClienteViewEntity, Long> {
}
