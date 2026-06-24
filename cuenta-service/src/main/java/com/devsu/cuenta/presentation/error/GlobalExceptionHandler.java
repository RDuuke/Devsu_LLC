package com.devsu.cuenta.presentation.error;

import com.devsu.cuenta.domain.exception.ClienteInexistenteException;
import com.devsu.cuenta.domain.exception.CuentaInactivaException;
import com.devsu.cuenta.domain.exception.CuentaNotFoundException;
import com.devsu.cuenta.domain.exception.DuplicateCuentaException;
import com.devsu.cuenta.domain.exception.MovimientoNotFoundException;
import com.devsu.cuenta.domain.exception.SaldoInsuficienteException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // F3: petición válida pero rechazada por regla de negocio, por eso 422 y no 400.
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<MessageResponse> handleSaldo(SaldoInsuficienteException ex) {
        return ResponseEntity.unprocessableEntity().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<MessageResponse> handleRateLimit(RequestNotPermitted ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new MessageResponse("Demasiadas solicitudes, intente más tarde"));
    }

    @ExceptionHandler({CuentaNotFoundException.class, MovimientoNotFoundException.class, ClienteInexistenteException.class})
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(DuplicateCuentaException.class)
    public ResponseEntity<ApiError> handleDuplicate(DuplicateCuentaException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req);
    }

    @ExceptionHandler({CuentaInactivaException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiError> handleUnprocessable(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));
        ApiError body = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                "Bad Request", "Error de validación", req.getRequestURI(), fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", req);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, HttpServletRequest req) {
        return ResponseEntity.status(status)
                .body(ApiError.of(status.value(), status.getReasonPhrase(), message, req.getRequestURI()));
    }
}
