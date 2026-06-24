package com.devsu.cliente.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Cliente extends Persona {

    private String clienteId;

    private String contrasena;

    private boolean estado;

    public void activar() {
        this.estado = true;
    }

    public void desactivar() {
        this.estado = false;
    }
}
