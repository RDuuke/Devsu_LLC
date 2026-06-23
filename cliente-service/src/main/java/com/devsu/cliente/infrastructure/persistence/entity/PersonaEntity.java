package com.devsu.cliente.infrastructure.persistence.entity;

import com.devsu.cliente.domain.model.Genero;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Entidad JPA base. Herencia JOINED: tablas {@code persona} y {@code cliente} unidas por PK. */
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private Integer edad;

    @Column(unique = true, nullable = false)
    private String identificacion;

    private String direccion;

    private String telefono;
}
