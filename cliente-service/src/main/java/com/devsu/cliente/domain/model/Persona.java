package com.devsu.cliente.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Persona de dominio. POJO sin anotaciones de JPA ni framework, a propósito. */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Persona {

    private Long id;
    private String nombre;
    private Genero genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
