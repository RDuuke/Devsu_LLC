package com.devsu.cliente.domain.port;

/** Puerto para hashear y verificar contraseñas. */
public interface PasswordHasher {
    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}
