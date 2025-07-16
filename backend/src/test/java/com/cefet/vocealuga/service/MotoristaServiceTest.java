package com.cefet.vocealuga.service;

import com.cefet.vocealuga.dtos.MeDTO;
import com.cefet.vocealuga.dtos.MotoristaDTO;
import com.cefet.vocealuga.entities.Cliente;
import com.cefet.vocealuga.entities.Motorista;
import com.cefet.vocealuga.entities.Usuario;
import com.cefet.vocealuga.repositories.MotoristaRepository;
import com.cefet.vocealuga.repositories.UsuarioRepository;
import com.cefet.vocealuga.services.AuthService;
import com.cefet.vocealuga.services.MotoristaService;
import com.cefet.vocealuga.services.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MotoristaServiceTest {

    private MotoristaRepository motoristaRepository;
    private MotoristaService motoristaService;
    private AuthService authService;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() throws Exception {
        // Criação dos mocks
        motoristaRepository = mock(MotoristaRepository.class);
        authService = mock(AuthService.class);
        usuarioRepository = mock(UsuarioRepository.class);

        // Instancia o serviço
        motoristaService = new MotoristaService();

        // Injeta os mocks usando reflexão
        var repositoryField = MotoristaService.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        repositoryField.set(motoristaService, motoristaRepository);

        var authServiceField = MotoristaService.class.getDeclaredField("authService");
        authServiceField.setAccessible(true);
        authServiceField.set(motoristaService, authService);

        var usuarioRepositoryField = MotoristaService.class.getDeclaredField("usuarioRepository");
        usuarioRepositoryField.setAccessible(true);
        usuarioRepositoryField.set(motoristaService, usuarioRepository);
    }

    @Test
    void deveCriarMotorista() {
        // Dados do motorista
        MotoristaDTO dto = new MotoristaDTO();
        dto.setNome("João Silva");
        dto.setCnh("12345678901");
        dto.setCpf("123.456.789-00");
        dto.setDataNascimento(LocalDate.of(1990, 5, 15));

        // Configurar mock
        when(motoristaRepository.save(any(Motorista.class))).thenAnswer(invocation -> {
            Motorista m = invocation.getArgument(0);
            m.setId(1L);
            return m;
        });

        // Executar método
        MotoristaDTO resultado = motoristaService.criarMotorista(dto);

        // Verificar resultado
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("12345678901", resultado.getCnh());
        assertEquals("123.456.789-00", resultado.getCpf());
        assertEquals(LocalDate.of(1990, 5, 15), resultado.getDataNascimento());

        // Verificar que o save foi chamado
        ArgumentCaptor<Motorista> motoristaCaptor = ArgumentCaptor.forClass(Motorista.class);
        verify(motoristaRepository).save(motoristaCaptor.capture());
        Motorista motoristaSalvo = motoristaCaptor.getValue();
        assertEquals("João Silva", motoristaSalvo.getNome());
        assertEquals("12345678901", motoristaSalvo.getCnh());
    }

    @Test
    void deveCriarMotoristaPeloUsuarioLogado() {
        // Dados do usuário
        MeDTO userInfo = new MeDTO();
        userInfo.setNome("Maria Souza");
        userInfo.setDocumento("987.654.321-00");
        userInfo.setDataDeNascimento(LocalDate.of(1985, 10, 20));
        userInfo.setEmail("maria@email.com");
        userInfo.setRole("ROLE_CLIENTE");

        Cliente cliente = new Cliente();
        cliente.setId(2L);
        cliente.setNome("Maria Souza");
        cliente.setEmail("maria@email.com");

        // Configurar mocks
        Authentication authentication = mock(Authentication.class);
        when(authService.findMe(authentication)).thenReturn(userInfo);
        when(usuarioRepository.findByEmail("maria@email.com")).thenReturn(cliente);
        when(motoristaRepository.save(any(Motorista.class))).thenAnswer(invocation -> {
            Motorista m = invocation.getArgument(0);
            m.setId(2L);
            return m;
        });

        // Executar método
        MotoristaDTO resultado = motoristaService.criarMotoristaPeloUsuarioLogado("98765432109", authentication);

        // Verificar resultado
        assertEquals(2L, resultado.getId());
        assertEquals("Maria Souza", resultado.getNome());
        assertEquals("98765432109", resultado.getCnh());
        assertEquals("987.654.321-00", resultado.getCpf());
        assertEquals(LocalDate.of(1985, 10, 20), resultado.getDataNascimento());
    }

    @Test
    void naoDeveCriarMotoristaParaUsuarioNaoCliente() {
        // Dados do usuário não-cliente
        MeDTO userInfo = new MeDTO();
        userInfo.setRole("ROLE_ADMIN");

        // Configurar mock
        Authentication authentication = mock(Authentication.class);
        when(authService.findMe(authentication)).thenReturn(userInfo);

        // Verificar exceção
        BusinessException exception = assertThrows(BusinessException.class,
                () -> motoristaService.criarMotoristaPeloUsuarioLogado("12345678901", authentication));

        assertEquals("Apenas clientes podem adicionar-se como motoristas", exception.getMessage());
    }

    @Test
    void naoDeveCriarMotoristaParaUsuarioNaoEncontrado() {
        MeDTO userInfo = new MeDTO();
        userInfo.setRole("ROLE_CLIENTE");
        userInfo.setEmail("naoexiste@email.com");

        Usuario usuario = new Usuario();

        Authentication authentication = mock(Authentication.class);
        when(authService.findMe(authentication)).thenReturn(userInfo);
        when(usuarioRepository.findByEmail("naoexiste@email.com")).thenReturn(usuario);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> motoristaService.criarMotoristaPeloUsuarioLogado("12345678901", authentication));

        assertEquals("Usuário não é um cliente", exception.getMessage());
    }
}