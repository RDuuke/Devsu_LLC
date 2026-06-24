package com.devsu.cuenta.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cliente_view")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ClienteViewEntity {

    @Id
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean estado;
}
