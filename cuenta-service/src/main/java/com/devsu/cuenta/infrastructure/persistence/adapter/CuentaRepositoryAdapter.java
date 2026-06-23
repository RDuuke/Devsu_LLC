package com.devsu.cuenta.infrastructure.persistence.adapter;

import com.devsu.cuenta.domain.model.Cuenta;
import com.devsu.cuenta.domain.port.CuentaRepository;
import com.devsu.cuenta.infrastructure.persistence.jpa.CuentaJpaRepository;
import com.devsu.cuenta.infrastructure.persistence.mapper.CuentaPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CuentaRepositoryAdapter implements CuentaRepository {

    private final CuentaJpaRepository jpa;
    private final CuentaPersistenceMapper mapper;

    @Override
    public Cuenta save(Cuenta cuenta) {
        return mapper.toDomain(jpa.save(mapper.toEntity(cuenta)));
    }

    @Override
    public Optional<Cuenta> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Cuenta> findByIdForUpdate(Long id) {
        return jpa.findByIdForUpdate(id).map(mapper::toDomain);
    }

    @Override
    public List<Cuenta> findAll(int page, int size) {
        return jpa.findAll(PageRequest.of(page, size, Sort.by("id").ascending()))
                .map(mapper::toDomain)
                .getContent();
    }

    @Override
    public boolean existsByNumeroCuenta(String numeroCuenta) {
        return jpa.existsByNumeroCuenta(numeroCuenta);
    }
}
