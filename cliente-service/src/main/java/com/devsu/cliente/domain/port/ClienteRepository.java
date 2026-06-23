package com.devsu.cliente.domain.port;

import com.devsu.cliente.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

/** Puerto de persistencia. El dominio define la interfaz; la infra la implementa con JPA. */
public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Optional<Cliente> findById(Long id);

    List<Cliente> findAll(int page, int size);

    boolean existsByIdentificacion(String identificacion);

    boolean existsByClienteId(String clienteId);

    void deleteById(Long id);
}
