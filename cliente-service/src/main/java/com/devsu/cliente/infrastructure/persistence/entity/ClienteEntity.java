package com.devsu.cliente.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Entidad JPA. Herencia JOINED: la tabla {@code cliente} comparte PK con {@code persona}. */
@Entity
@Table(name = "cliente")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ClienteEntity extends PersonaEntity {

    @Column(name = "cliente_id", unique = true, nullable = false)
    private String clienteId;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private boolean estado;
}
