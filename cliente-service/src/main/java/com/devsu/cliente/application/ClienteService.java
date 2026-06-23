package com.devsu.cliente.application;

import com.devsu.cliente.application.command.ActualizarClienteCommand;
import com.devsu.cliente.application.command.CrearClienteCommand;
import com.devsu.cliente.domain.event.ClienteEvent;
import com.devsu.cliente.domain.event.ClienteEventType;
import com.devsu.cliente.domain.exception.ClienteNotFoundException;
import com.devsu.cliente.domain.exception.DuplicateClienteException;
import com.devsu.cliente.domain.model.Cliente;
import com.devsu.cliente.domain.port.ClienteRepository;
import com.devsu.cliente.domain.port.EventPublisher;
import com.devsu.cliente.domain.port.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * CRUD de clientes. La entidad y su evento se persisten en la MISMA transacción
 * vía Outbox, así que o se guardan ambos o ninguno.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EventPublisher eventPublisher;
    private final PasswordHasher passwordHasher;

    @Transactional
    public Cliente crear(CrearClienteCommand cmd) {
        if (clienteRepository.existsByIdentificacion(cmd.identificacion())) {
            throw new DuplicateClienteException("identificacion", cmd.identificacion());
        }
        if (clienteRepository.existsByClienteId(cmd.clienteId())) {
            throw new DuplicateClienteException("clienteId", cmd.clienteId());
        }

        Cliente cliente = Cliente.builder()
                .nombre(cmd.nombre())
                .genero(cmd.genero())
                .edad(cmd.edad())
                .identificacion(cmd.identificacion())
                .direccion(cmd.direccion())
                .telefono(cmd.telefono())
                .clienteId(cmd.clienteId())
                .contrasena(passwordHasher.hash(cmd.contrasena()))
                .estado(cmd.estado() == null || cmd.estado())
                .build();

        Cliente guardado = clienteRepository.save(cliente);
        publicar(ClienteEventType.CLIENTE_CREADO, guardado);
        return guardado;
    }

    @Transactional(readOnly = true)
    public Cliente obtener(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar(int page, int size) {
        return clienteRepository.findAll(page, size);
    }

    @Transactional
    public Cliente actualizar(Long id, ActualizarClienteCommand cmd) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        if (cmd.nombre() != null) cliente.setNombre(cmd.nombre());
        if (cmd.genero() != null) cliente.setGenero(cmd.genero());
        if (cmd.edad() != null) cliente.setEdad(cmd.edad());
        if (cmd.direccion() != null) cliente.setDireccion(cmd.direccion());
        if (cmd.telefono() != null) cliente.setTelefono(cmd.telefono());
        if (cmd.contrasena() != null) cliente.setContrasena(passwordHasher.hash(cmd.contrasena()));
        if (cmd.estado() != null) cliente.setEstado(cmd.estado());

        Cliente actualizado = clienteRepository.save(cliente);
        publicar(ClienteEventType.CLIENTE_ACTUALIZADO, actualizado);
        return actualizado;
    }

    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        clienteRepository.deleteById(id);
        publicar(ClienteEventType.CLIENTE_ELIMINADO, cliente);
    }

    private void publicar(ClienteEventType type, Cliente cliente) {
        eventPublisher.publish(ClienteEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .type(type)
                .clienteId(cliente.getId())
                .nombre(cliente.getNombre())
                .estado(cliente.isEstado())
                .build());
    }
}
