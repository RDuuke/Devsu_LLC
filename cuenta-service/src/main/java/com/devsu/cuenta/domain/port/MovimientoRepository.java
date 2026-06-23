package com.devsu.cuenta.domain.port;

import com.devsu.cuenta.domain.model.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoRepository {

    Movimiento save(Movimiento movimiento);

    Optional<Movimiento> findById(Long id);

    List<Movimiento> findAll(int page, int size);
}
