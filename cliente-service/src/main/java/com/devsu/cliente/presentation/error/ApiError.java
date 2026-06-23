package com.devsu.cliente.presentation.error;

import java.time.Instant;
import java.util.Map;

/** Cuerpo de error uniforme para toda la API. */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(Instant.now(), status, error, message, path, null);
    }
}
