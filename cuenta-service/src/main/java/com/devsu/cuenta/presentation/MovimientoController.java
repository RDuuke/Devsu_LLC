package com.devsu.cuenta.presentation;

import com.devsu.cuenta.application.MovimientoService;
import com.devsu.cuenta.domain.model.Movimiento;
import com.devsu.cuenta.presentation.dto.MovimientoRequest;
import com.devsu.cuenta.presentation.dto.MovimientoResponse;
import com.devsu.cuenta.presentation.dto.MovimientoUpdateRequest;
import com.devsu.cuenta.presentation.mapper.MovimientoApiMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/** API REST de movimientos (F2/F3). */
@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;
    private final MovimientoApiMapper mapper;

    @GetMapping
    public List<MovimientoResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return movimientoService.listar(page, size).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public MovimientoResponse obtener(@PathVariable Long id) {
        return mapper.toResponse(movimientoService.obtener(id));
    }

    @PostMapping
    @RateLimiter(name = "movimientos")
    public ResponseEntity<MovimientoResponse> registrar(
            @Valid @RequestBody MovimientoRequest request,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
            UriComponentsBuilder uriBuilder) {
        Movimiento mov = movimientoService.registrar(mapper.toCommand(request), idempotencyKey);
        URI location = uriBuilder.path("/movimientos/{id}").buildAndExpand(mov.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(mov));
    }

    @PutMapping("/{id}")
    @RateLimiter(name = "movimientos")
    public MovimientoResponse actualizar(@PathVariable Long id,
                                         @Valid @RequestBody MovimientoUpdateRequest request) {
        return mapper.toResponse(
                movimientoService.actualizar(id, request.tipoMovimiento(), request.valor()));
    }
}
