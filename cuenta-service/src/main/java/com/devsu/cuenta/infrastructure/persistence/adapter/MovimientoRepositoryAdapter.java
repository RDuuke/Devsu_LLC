package com.devsu.cuenta.infrastructure.persistence.adapter;

import com.devsu.cuenta.domain.model.Movimiento;
import com.devsu.cuenta.domain.port.MovimientoRepository;
import com.devsu.cuenta.infrastructure.persistence.jpa.MovimientoJpaRepository;
import com.devsu.cuenta.infrastructure.persistence.mapper.MovimientoPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovimientoRepositoryAdapter implements MovimientoRepository {

    private final MovimientoJpaRepository jpa;
    private final MovimientoPersistenceMapper mapper;

    @Override
    public Movimiento save(Movimiento movimiento) {
        return mapper.toDomain(jpa.save(mapper.toEntity(movimiento)));
    }

    @Override
    public Optional<Movimiento> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Movimiento> findAll(int page, int size) {
        return jpa.findAll(PageRequest.of(page, size, Sort.by("fecha").descending()))
                .map(mapper::toDomain)
                .getContent();
    }
}
