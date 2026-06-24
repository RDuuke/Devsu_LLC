package com.devsu.cuenta.application;

import com.devsu.cuenta.domain.model.ClienteView;
import com.devsu.cuenta.domain.port.ClienteViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteProjectionService {

    private final ClienteViewRepository clienteViewRepository;

    @Transactional
    public void aplicarUpsert(Long clienteId, String nombre, boolean estado) {
        ClienteView view = clienteViewRepository.findById(clienteId)
                .orElseGet(() -> ClienteView.builder().clienteId(clienteId).build());
        view.setNombre(nombre);
        view.setEstado(estado);
        clienteViewRepository.save(view);
    }

    @Transactional
    public void aplicarEliminacion(Long clienteId) {
        clienteViewRepository.deleteById(clienteId);
    }
}
