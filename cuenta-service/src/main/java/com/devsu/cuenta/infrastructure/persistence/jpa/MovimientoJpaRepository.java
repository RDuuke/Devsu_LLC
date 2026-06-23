package com.devsu.cuenta.infrastructure.persistence.jpa;

import com.devsu.cuenta.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {
}
