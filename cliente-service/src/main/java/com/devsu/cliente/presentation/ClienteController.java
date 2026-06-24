package com.devsu.cliente.presentation;

import com.devsu.cliente.application.ClienteService;
import com.devsu.cliente.domain.model.Cliente;
import com.devsu.cliente.presentation.dto.ClienteRequest;
import com.devsu.cliente.presentation.dto.ClienteResponse;
import com.devsu.cliente.presentation.dto.ClienteUpdateRequest;
import com.devsu.cliente.presentation.mapper.ClienteApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteApiMapper mapper;

    @GetMapping
    public List<ClienteResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return clienteService.listar(page, size).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ClienteResponse obtener(@PathVariable Long id) {
        return mapper.toResponse(clienteService.obtener(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@Valid @RequestBody ClienteRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        Cliente creado = clienteService.crear(mapper.toCommand(request));
        URI location = uriBuilder.path("/clientes/{id}").buildAndExpand(creado.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ClienteResponse actualizar(@PathVariable Long id,
                                      @Valid @RequestBody ClienteUpdateRequest request) {
        return mapper.toResponse(clienteService.actualizar(id, mapper.toCommand(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
    }
}
