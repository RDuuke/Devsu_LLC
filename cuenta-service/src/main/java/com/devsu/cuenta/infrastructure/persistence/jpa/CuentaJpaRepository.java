package com.devsu.cuenta.infrastructure.persistence.jpa;

import com.devsu.cuenta.infrastructure.persistence.entity.CuentaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, Long> {

    boolean existsByNumeroCuenta(String numeroCuenta);

    /** Serializa movimientos concurrentes sobre la misma cuenta. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CuentaEntity c where c.id = :id")
    Optional<CuentaEntity> findByIdForUpdate(@Param("id") Long id);
}
