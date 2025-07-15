package com.cefet.vocealuga.service;
import com.cefet.vocealuga.dtos.MeDTO;
import com.cefet.vocealuga.dtos.ReservaDTO;
import com.cefet.vocealuga.entities.*;
import com.cefet.vocealuga.entities.enums.StatusReserva;
import com.cefet.vocealuga.entities.enums.TipoReserva;
import com.cefet.vocealuga.repositories.FilialRepository;
import com.cefet.vocealuga.repositories.ReservaRepository;
import com.cefet.vocealuga.repositories.UsuarioRepository;
import com.cefet.vocealuga.services.AuthService;
import com.cefet.vocealuga.services.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
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
        dto.setDataVencimento(LocalDate.now());

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

    @Test
    void funcionarioBuscaReservaPorId() {
        Reserva reserva = new Reserva();
        reserva.setId(100L);
        reserva.setStatus(StatusReserva.PENDENTE);

        when(reservaRepository.findById(100L)).thenReturn(Optional.of(reserva));

        ReservaDTO dto = reservaService.findById(100L);

        assertEquals(100L, dto.getId());
        assertEquals(StatusReserva.PENDENTE, dto.getStatus());
    }

    @Test
    void gerenteBuscaReservaPorId() {
        Reserva reserva = new Reserva();
        reserva.setId(200L);
        reserva.setStatus(StatusReserva.AGENDADO);

        when(reservaRepository.findById(200L)).thenReturn(Optional.of(reserva));

        ReservaDTO dto = reservaService.findById(200L);

        assertEquals(200L, dto.getId());
        assertEquals(StatusReserva.AGENDADO, dto.getStatus());
    }

    @Test
    void administradorBuscaReservaPorId() {
        Reserva reserva = new Reserva();
        reserva.setId(300L);
        reserva.setStatus(StatusReserva.CANCELADO);

        when(reservaRepository.findById(300L)).thenReturn(Optional.of(reserva));

        ReservaDTO dto = reservaService.findById(300L);

        assertEquals(300L, dto.getId());
        assertEquals(StatusReserva.CANCELADO, dto.getStatus());
    }

    @Test
    void deveRetornarListaVaziaQuandoClienteNaoTemReservas() throws Exception {
        ReservaRepository reservaRepository = mock(ReservaRepository.class);
        AuthService authService = new AuthService();
        var field = AuthService.class.getDeclaredField("reservaRepository");
        field.setAccessible(true);
        field.set(authService, reservaRepository);

        Cliente cliente = new Cliente();
        cliente.setId(99L);
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");
        cliente.setTelefone("987654321");
        cliente.setDataDeNascimento(LocalDate.of(1985, 5, 20));
        cliente.setPontosFidelidade(50);
        cliente.setDocumento("98765432100");

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(cliente);
        when(reservaRepository.findIdsByUsuarioId(99L)).thenReturn(List.of());

        MeDTO dto = authService.findMe(auth);

        assertEquals("Maria", dto.getNome());
        assertEquals(List.of(), dto.getReservas());
    }

    @Test
    void deveAtualizarReservaPendenteComDadosValidos() {
        // Reserva original
        Reserva reserva = new Reserva();
        reserva.setId(400L);
        reserva.setStatus(StatusReserva.PENDENTE);
        reserva.setDataReserva(LocalDate.now());
        reserva.setDataVencimento(LocalDate.now().plusDays(2));
        reserva.setCategoria(TipoReserva.ANTECIPADA);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        reserva.setUsuario(usuario);

        Filial filial = new Filial();
        filial.setId(2L);
        reserva.setLocalRetirada(filial);

        // Mock busca reserva
        when(reservaRepository.findById(400L)).thenReturn(Optional.of(reserva));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(filialRepository.findById(3L)).thenReturn(Optional.of(filial));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(inv -> inv.getArgument(0));


        ReservaDTO dto = new ReservaDTO();
        dto.setId(400L);
        dto.setUsuarioId(1L);
        dto.setLocalRetiradaId(3L);
        dto.setCategoria(TipoReserva.IMEDIATA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now().plusDays(1));
        dto.setDataVencimento(LocalDate.now().plusDays(4));

        ReservaDTO atualizado = reservaService.update(400L, dto);

        assertEquals(400L, atualizado.getId());
        assertEquals(StatusReserva.PENDENTE, atualizado.getStatus());
        assertEquals(TipoReserva.IMEDIATA, atualizado.getCategoria());
        assertEquals(LocalDate.now().plusDays(1), atualizado.getDataReserva());
        assertEquals(LocalDate.now().plusDays(4), atualizado.getDataVencimento());
        assertEquals(3L, atualizado.getLocalRetiradaId());
    }

    @Test
    void deveImpedirAtualizacaoDeReservaAprovada() {
        Reserva reserva = new Reserva();
        reserva.setId(500L);
        reserva.setStatus(StatusReserva.EM_ANDAMENTO);
        reserva.setDataReserva(LocalDate.now());
        reserva.setDataVencimento(LocalDate.now().plusDays(2));
        reserva.setCategoria(TipoReserva.ANTECIPADA);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        reserva.setUsuario(usuario);

        Filial filial = new Filial();
        filial.setId(2L);
        reserva.setLocalRetirada(filial);

        when(reservaRepository.findById(500L)).thenReturn(Optional.of(reserva));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(filialRepository.findById(2L)).thenReturn(Optional.of(filial)); // Adicione esta linha

        ReservaDTO dto = new ReservaDTO();
        dto.setId(500L);
        dto.setUsuarioId(1L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.IMEDIATA);
        dto.setStatus(StatusReserva.EM_ANDAMENTO);
        dto.setDataReserva(LocalDate.now().plusDays(1));
        dto.setDataVencimento(LocalDate.now().plusDays(4));

        assertThrows(IllegalArgumentException.class, () -> reservaService.update(500L, dto));
    }

    @Test
    void deveImpedirAtualizacaoComDataVencimentoMenorOuIgualDataReserva() {
        // Reserva original
        Reserva reserva = new Reserva();
        reserva.setId(600L);
        reserva.setStatus(StatusReserva.PENDENTE);
        reserva.setDataReserva(LocalDate.now());
        reserva.setDataVencimento(LocalDate.now().plusDays(2));
        reserva.setCategoria(TipoReserva.ANTECIPADA);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        reserva.setUsuario(usuario);

        Filial filial = new Filial();
        filial.setId(2L);
        reserva.setLocalRetirada(filial);

        when(reservaRepository.findById(600L)).thenReturn(Optional.of(reserva));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(filialRepository.findById(2L)).thenReturn(Optional.of(filial));

        ReservaDTO dto = new ReservaDTO();
        dto.setId(600L);
        dto.setUsuarioId(1L);
        dto.setLocalRetiradaId(2L);
        dto.setCategoria(TipoReserva.IMEDIATA);
        dto.setStatus(StatusReserva.PENDENTE);
        dto.setDataReserva(LocalDate.now());
        dto.setDataVencimento(LocalDate.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservaService.update(600L, dto));

        assertEquals("Data de vencimento deve ser posterior à data de reserva", exception.getMessage());
    }

    @Test
    void deveImpedirAcessoNaoAutenticado() {
        org.springframework.security.core.context.SecurityContextHolder.clearContext();

        when(reservaRepository.findById(anyLong())).thenThrow(
                new RuntimeException("Acesso negado. É necessário fazer login"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.findById(1L);
        });

        assertEquals("Acesso negado. É necessário fazer login", exception.getMessage());
    }


}