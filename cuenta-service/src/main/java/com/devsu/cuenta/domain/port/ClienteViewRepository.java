package com.devsu.cuenta.domain.port;

import com.devsu.cuenta.domain.model.ClienteView;

import java.util.Optional;

/** Proyección local de clientes, poblada por eventos. */
public interface ClienteViewRepository {

    ClienteView save(ClienteView clienteView);

    Optional<ClienteView> findById(Long clienteId);

    void deleteById(Long clienteId);
}
