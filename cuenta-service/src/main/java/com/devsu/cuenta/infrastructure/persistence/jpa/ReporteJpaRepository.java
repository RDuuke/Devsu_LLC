package com.devsu.cuenta.infrastructure.persistence.jpa;

import com.devsu.cuenta.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReporteJpaRepository extends JpaRepository<MovimientoEntity, Long> {

    @Query(value = """
            SELECT m.fecha::date       AS fecha,
                   cv.nombre           AS cliente,
                   c.numero_cuenta     AS numeroCuenta,
                   c.tipo_cuenta       AS tipo,
                   c.saldo_inicial     AS saldoInicial,
                   c.estado            AS estado,
                   m.valor             AS movimiento,
                   m.saldo             AS saldoDisponible
            FROM movimiento m
            JOIN cuenta c        ON c.id = m.cuenta_id
            JOIN cliente_view cv ON cv.cliente_id = c.cliente_id
            WHERE c.cliente_id = :clienteId
              AND m.fecha >= :inicio
              AND m.fecha <  :finExclusive
            ORDER BY m.fecha
            """, nativeQuery = true)
    List<EstadoCuentaProjection> estadoCuenta(@Param("clienteId") Long clienteId,
                                              @Param("inicio") LocalDateTime inicio,
                                              @Param("finExclusive") LocalDateTime finExclusive);
}
