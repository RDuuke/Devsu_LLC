package com.devsu.cliente.infrastructure.persistence.adapter;

import com.devsu.cliente.domain.model.Cliente;
import com.devsu.cliente.domain.port.ClienteRepository;
import com.devsu.cliente.infrastructure.persistence.entity.ClienteEntity;
import com.devsu.cliente.infrastructure.persistence.jpa.ClienteJpaRepository;
import com.devsu.cliente.infrastructure.persistence.mapper.ClientePersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/** Implementa {@link ClienteRepository} sobre Spring Data JPA, mapeando con MapStruct. */
@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final ClienteJpaRepository jpa;
    private final ClientePersistenceMapper mapper;

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity saved = jpa.save(mapper.toEntity(cliente));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Cliente> findAll(int page, int size) {
        return jpa.findAll(PageRequest.of(page, size, Sort.by("id").ascending()))
                .map(mapper::toDomain)
                .getContent();
    }

    @Override
    public boolean existsByIdentificacion(String identificacion) {
        return jpa.existsByIdentificacion(identificacion);
    }

    @Override
    public boolean existsByClienteId(String clienteId) {
        return jpa.existsByClienteId(clienteId);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
