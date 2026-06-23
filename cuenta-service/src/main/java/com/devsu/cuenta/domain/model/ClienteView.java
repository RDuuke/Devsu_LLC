package com.devsu.cuenta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Proyección local de Cliente poblada por eventos de cliente-service; el reporte no llama al otro servicio. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteView {
    private Long clienteId;
    private String nombre;
    private boolean estado;
}
