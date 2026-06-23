package com.devsu.cuenta.presentation;

import com.devsu.cuenta.application.CuentaService;
import com.devsu.cuenta.domain.model.Cuenta;
import com.devsu.cuenta.presentation.dto.CuentaRequest;
import com.devsu.cuenta.presentation.dto.CuentaResponse;
import com.devsu.cuenta.presentation.dto.CuentaUpdateRequest;
import com.devsu.cuenta.presentation.mapper.CuentaApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/** API REST de cuentas (F1). */
@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;
    private final CuentaApiMapper mapper;

    @GetMapping
    public List<CuentaResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return cuentaService.listar(page, size).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public CuentaResponse obtener(@PathVariable Long id) {
        return mapper.toResponse(cuentaService.obtener(id));
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> crear(@Valid @RequestBody CuentaRequest request,
                                                UriComponentsBuilder uriBuilder) {
        Cuenta creada = cuentaService.crear(mapper.toCommand(request));
        URI location = uriBuilder.path("/cuentas/{id}").buildAndExpand(creada.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(creada));
    }

    @PutMapping("/{id}")
    public CuentaResponse actualizar(@PathVariable Long id,
                                     @Valid @RequestBody CuentaUpdateRequest request) {
        return mapper.toResponse(cuentaService.actualizar(id, mapper.toCommand(request)));
    }
}
