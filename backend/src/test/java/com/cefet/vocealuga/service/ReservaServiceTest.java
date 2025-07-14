package com.cefet.vocealuga.service;
import com.cefet.vocealuga.dtos.ReservaDTO;
import com.cefet.vocealuga.entities.*;
import com.cefet.vocealuga.entities.enums.StatusReserva;
import com.cefet.vocealuga.entities.enums.TipoReserva;
import com.cefet.vocealuga.repositories.FilialRepository;
import com.cefet.vocealuga.repositories.ReservaRepository;
import com.cefet.vocealuga.repositories.UsuarioRepository;
import com.cefet.vocealuga.services.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ReservaServiceTest {

    private ReservaRepository reservaRepository;
    private FilialRepository filialRepository;
    private UsuarioRepository usuarioRepository;
    private ReservaService reservaService;

    @BeforeEach
    void setUp() throws Exception {
        reservaRepository = mock(ReservaRepository.class);
        filialRepository = mock(FilialRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        reservaService = new ReservaService();

        var reservaField = ReservaService.class.getDeclaredField("repository");
        reservaField.setAccessible(true);
        reservaField.set(reservaService, reservaRepository);

        var filialField = ReservaService.class.getDeclaredField("filialRepository");
        filialField.setAccessible(true);
        filialField.set(reservaService, filialRepository);

        var usuarioField = ReservaService.class.getDeclaredField("usuarioRepository");
        usuarioField.setAccessible(true);
        usuarioField.set(reservaService, usuarioRepository);
    }

    @Test
    void deveCriarReservaComStatusPendente() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(1L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.ANTECIPADA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now().plusDays(3));

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Filial filial = new Filial();
        filial.setId(2L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(filialRepository.findById(2L)).thenReturn(Optional.of(filial));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReservaDTO result = reservaService.insert(dto);

        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(reservaRepository).save(captor.capture());
        Reserva reservaSalva = captor.getValue();

        assertEquals(StatusReserva.PENDENTE, reservaSalva.getStatus());
        assertEquals(StatusReserva.PENDENTE, result.getStatus());
    }


    @Test
    void deveCriarReservaImediataPorFuncionario() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(10L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.IMEDIATA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now().plusDays(1));

        Funcionario funcionario = new Funcionario();
        funcionario.setId(10L);

        Filial filial = new Filial();
        filial.setId(2L);

        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(funcionario));
        when(filialRepository.findById(2L)).thenReturn(Optional.of(filial));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReservaDTO result = reservaService.insert(dto);

        assertEquals(StatusReserva.PENDENTE, result.getStatus());
        assertEquals(TipoReserva.IMEDIATA, result.getCategoria());
    }

    @Test
    void deveCriarReservaImediataPorGerente() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(20L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.IMEDIATA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now().plusDays(1));

        Gerente gerente = new Gerente();
        gerente.setId(20L);

        Filial filial = new Filial();
        filial.setId(2L);

        when(usuarioRepository.findById(20L)).thenReturn(Optional.of(gerente));
        when(filialRepository.findById(2L)).thenReturn(Optional.of(filial));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReservaDTO result = reservaService.insert(dto);

        assertEquals(StatusReserva.PENDENTE, result.getStatus());
        assertEquals(TipoReserva.IMEDIATA, result.getCategoria());
    }

    @Test
    void deveCriarReservaImediataPorAdministrador() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(30L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.IMEDIATA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now().plusDays(1));

        Administrador admin = new Administrador();
        admin.setId(30L);

        Filial filial = new Filial();
        filial.setId(2L);

        when(usuarioRepository.findById(30L)).thenReturn(Optional.of(admin));
        when(filialRepository.findById(2L)).thenReturn(Optional.of(filial));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReservaDTO result = reservaService.insert(dto);

        assertEquals(StatusReserva.PENDENTE, result.getStatus());
        assertEquals(TipoReserva.IMEDIATA, result.getCategoria());
    }

    @Test
    void deveFalharQuandoDataVencimentoMenorOuIgualDataReserva() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(1L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.ANTECIPADA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now()); // igual à data de início

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Filial filial = new Filial();
        filial.setId(2L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(filialRepository.findById(2L)).thenReturn(Optional.of(filial));

        assertThrows(IllegalArgumentException.class, () -> reservaService.insert(dto));
    }

    @Test
    void deveFalharQuandoUsuarioIdAusente() {
        ReservaDTO dto = new ReservaDTO();
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.ANTECIPADA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class, () -> reservaService.insert(dto));
    }

    @Test
    void deveFalharQuandoLocalRetiradaIdAusente() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(1L);
        dto.setCategoria(TipoReserva.ANTECIPADA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class, () -> reservaService.insert(dto));
    }

    @Test
    void deveFalharQuandoDataReservaAusente() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(1L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.ANTECIPADA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataVencimento(LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class, () -> reservaService.insert(dto));
    }

    @Test
    void deveFalharQuandoDataVencimentoAusente() {
        ReservaDTO dto = new ReservaDTO();
        dto.setUsuarioId(1L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.ANTECIPADA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> reservaService.insert(dto));
    }
}