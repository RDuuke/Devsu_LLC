package com.devsu.cliente.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Cliente de dominio; hereda de {@link Persona}. */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Cliente extends Persona {

    /** Id de negocio, distinto del id técnico. */
    private String clienteId;

    /** Siempre hash BCrypt, nunca texto plano. */
    private String contrasena;

    private boolean estado;

    public void activar() {
        this.estado = true;
    }

    public void desactivar() {
        this.estado = false;
    }
}
