package com.devsu.cliente.application;

import com.devsu.cliente.application.command.CrearClienteCommand;
import com.devsu.cliente.domain.event.ClienteEvent;
import com.devsu.cliente.domain.event.ClienteEventType;
import com.devsu.cliente.domain.exception.DuplicateClienteException;
import com.devsu.cliente.domain.model.Cliente;
import com.devsu.cliente.domain.model.Genero;
import com.devsu.cliente.domain.port.ClienteRepository;
import com.devsu.cliente.domain.port.EventPublisher;
import com.devsu.cliente.domain.port.PasswordHasher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private EventPublisher eventPublisher;
    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private ClienteService clienteService;

    private CrearClienteCommand cmd() {
        return new CrearClienteCommand("Marianela Montalvo", Genero.FEMENINO, 30,
                "0102030405", "Quito", "0991234567", "marianela", "claveSegura", true);
    }

    @Test
    void crear_hasheaContrasenaYPublicaEvento() {
        when(passwordHasher.hash("claveSegura")).thenReturn("$2a$hashed");
        when(clienteRepository.existsByIdentificacion(any())).thenReturn(false);
        when(clienteRepository.existsByClienteId(any())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> {
            Cliente c = inv.getArgument(0);
            c.setId(1L);
            return c;
        });

        Cliente creado = clienteService.crear(cmd());

        assertThat(creado.getContrasena()).isEqualTo("$2a$hashed");
        assertThat(creado.isEstado()).isTrue();

        ArgumentCaptor<ClienteEvent> captor = ArgumentCaptor.forClass(ClienteEvent.class);
        verify(eventPublisher).publish(captor.capture());
        assertThat(captor.getValue().type()).isEqualTo(ClienteEventType.CLIENTE_CREADO);
        assertThat(captor.getValue().clienteId()).isEqualTo(1L);
    }

    @Test
    void crear_conIdentificacionDuplicada_lanzaExcepcion() {
        when(clienteRepository.existsByIdentificacion(any())).thenReturn(true);

        assertThatThrownBy(() -> clienteService.crear(cmd()))
                .isInstanceOf(DuplicateClienteException.class);

        verify(clienteRepository, never()).save(any());
        verify(eventPublisher, never()).publish(any());
    }
}
