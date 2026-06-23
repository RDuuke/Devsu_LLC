package com.devsu.cuenta.infrastructure.persistence.adapter;

import com.devsu.cuenta.domain.model.ClienteView;
import com.devsu.cuenta.domain.port.ClienteViewRepository;
import com.devsu.cuenta.infrastructure.persistence.jpa.ClienteViewJpaRepository;
import com.devsu.cuenta.infrastructure.persistence.mapper.ClienteViewPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteViewRepositoryAdapter implements ClienteViewRepository {

    private final ClienteViewJpaRepository jpa;
    private final ClienteViewPersistenceMapper mapper;

    @Override
    public ClienteView save(ClienteView clienteView) {
        return mapper.toDomain(jpa.save(mapper.toEntity(clienteView)));
    }

    @Override
    public Optional<ClienteView> findById(Long clienteId) {
        return jpa.findById(clienteId).map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long clienteId) {
        jpa.deleteById(clienteId);
    }
}
