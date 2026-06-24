package com.devsu.cuenta.infrastructure.persistence.entity;

import com.devsu.cuenta.domain.model.TipoCuenta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoInicial;

    @Column(name = "saldo_actual", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoActual;

    @Column(nullable = false)
    private boolean estado;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Version
    private Long version;
}
