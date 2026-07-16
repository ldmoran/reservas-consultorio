package com.reservas.service;

import com.reservas.dto.ReservaRequest;
import com.reservas.dto.ReservaResponse;
import com.reservas.entity.Reserva;
import com.reservas.entity.Servicio;
import com.reservas.entity.Usuario;
import com.reservas.repository.ReservaRepository;
import com.reservas.repository.ServicioRepository;
import com.reservas.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    // Verifica que se crea un nuevo usuario y una reserva cuando no existe el email dado.
    void crearReserva_shouldCreateNewUserAndReservation_whenUsuarioDoesNotExist() {
        ReservaRequest request = new ReservaRequest();
        request.setNombre("Ana Perez");
        request.setTelefono("123456789");
        request.setEmail("ana@example.com");
        request.setIdServicio(5L);
        request.setFecha(LocalDate.of(2026, 7, 20));
        request.setHora(LocalTime.of(10, 30));
        request.setObservaciones("Necesita consulta rápida");

        Servicio servicio = new Servicio();
        servicio.setIdServicio(5L);
        servicio.setNombreServicio("Corte de cabello");

        Usuario nuevoUsuario = new Usuario("Ana Perez", "123456789", "ana@example.com");
        nuevoUsuario.setIdUsuario(7L);

        Reserva reservaGuardada = new Reserva();
        reservaGuardada.setIdReserva(12L);
        reservaGuardada.setUsuario(nuevoUsuario);
        reservaGuardada.setServicio(servicio);
        reservaGuardada.setFecha(request.getFecha());
        reservaGuardada.setHora(request.getHora());
        reservaGuardada.setEstado("Pendiente");
        reservaGuardada.setObservaciones(request.getObservaciones());

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);
        when(servicioRepository.findById(request.getIdServicio())).thenReturn(Optional.of(servicio));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        ReservaResponse response = reservaService.crearReserva(request);

        assertNotNull(response);
        assertEquals(12L, response.getIdReserva());
        assertEquals("Ana Perez", response.getNombreCliente());
        assertEquals("ana@example.com", response.getEmailCliente());
        assertEquals("Corte de cabello", response.getNombreServicio());
        assertEquals("Pendiente", response.getEstado());
        assertEquals("Necesita consulta rápida", response.getObservaciones());

        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioCaptor.capture());
        assertEquals("Ana Perez", usuarioCaptor.getValue().getNombre());
        assertEquals("ana@example.com", usuarioCaptor.getValue().getEmail());

        verify(reservaRepository).save(any(Reserva.class));
    }

    @Test
    // Verifica que el estado de una reserva existente se actualiza correctamente.
    void actualizarEstadoReserva_shouldReturnUpdatedReserva_whenReservaExists() {
        Usuario usuario = new Usuario("Luis Gomez", "987654321", "luis@example.com");
        Servicio servicio = new Servicio();
        servicio.setIdServicio(3L);
        servicio.setNombreServicio("Masaje relajante");

        Reserva reserva = new Reserva();
        reserva.setIdReserva(21L);
        reserva.setUsuario(usuario);
        reserva.setServicio(servicio);
        reserva.setFecha(LocalDate.of(2026, 8, 1));
        reserva.setHora(LocalTime.of(15, 0));
        reserva.setEstado("Pendiente");

        Reserva reservaActualizada = new Reserva();
        reservaActualizada.setIdReserva(21L);
        reservaActualizada.setUsuario(usuario);
        reservaActualizada.setServicio(servicio);
        reservaActualizada.setFecha(reserva.getFecha());
        reservaActualizada.setHora(reserva.getHora());
        reservaActualizada.setEstado("Confirmada");

        when(reservaRepository.findById(21L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(reserva)).thenReturn(reservaActualizada);

        ReservaResponse response = reservaService.actualizarEstadoReserva(21L, "Confirmada");

        assertNotNull(response);
        assertEquals(21L, response.getIdReserva());
        assertEquals("Confirmada", response.getEstado());
        verify(reservaRepository).findById(21L);
        verify(reservaRepository).save(reserva);
    }

    @Test
    // Verifica que se obtienen reservas filtradas por estado y se mapean a respuestas.
    void obtenerReservasPorEstado_shouldReturnMappedResponses() {
        Usuario usuario1 = new Usuario("Mariana", "5551234", "mariana@example.com");
        Usuario usuario2 = new Usuario("Pedro", "5555678", "pedro@example.com");
        Servicio servicio = new Servicio();
        servicio.setNombreServicio("Evaluación médica");

        Reserva reserva1 = new Reserva();
        reserva1.setIdReserva(31L);
        reserva1.setUsuario(usuario1);
        reserva1.setServicio(servicio);
        reserva1.setFecha(LocalDate.of(2026, 7, 17));
        reserva1.setHora(LocalTime.of(9, 0));
        reserva1.setEstado("Pendiente");

        Reserva reserva2 = new Reserva();
        reserva2.setIdReserva(32L);
        reserva2.setUsuario(usuario2);
        reserva2.setServicio(servicio);
        reserva2.setFecha(LocalDate.of(2026, 7, 18));
        reserva2.setHora(LocalTime.of(11, 0));
        reserva2.setEstado("Pendiente");

        when(reservaRepository.findByEstado("Pendiente")).thenReturn(List.of(reserva1, reserva2));

        List<ReservaResponse> responses = reservaService.obtenerReservasPorEstado("Pendiente");

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Pendiente", responses.get(0).getEstado());
        assertEquals("Pendiente", responses.get(1).getEstado());
        assertEquals("Mariana", responses.get(0).getNombreCliente());
        assertEquals("Pedro", responses.get(1).getNombreCliente());
    }
}
