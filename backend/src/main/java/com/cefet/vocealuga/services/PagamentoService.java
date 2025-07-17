package com.cefet.vocealuga.services;

import com.cefet.vocealuga.dtos.PagamentoDTO;
import com.cefet.vocealuga.entities.Pagamento;
import com.cefet.vocealuga.entities.Usuario;
import com.cefet.vocealuga.repositories.PagamentoRepository;
import com.cefet.vocealuga.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private EmailService emailService;

    @Value("${app.base.url:http://localhost:8080}")
    private String baseUrl;

    @Transactional
    public PagamentoDTO processarPagamento(PagamentoDTO dto) {
        // Validações básicas
        validarDadosPagamento(dto);

        // Obter usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Gerar token único para confirmação
        String token = UUID.randomUUID().toString();

        // Criar registro temporário do pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setToken(token);
        pagamento.setMetodo(dto.getMetodo());
        pagamento.setCardNumber(mascaraCartao(dto.getCardNumber()));
        pagamento.setCardExpiry(dto.getCardExpiry());
        pagamento.setCardCVV("***"); // Nunca salvar CVV real
        pagamento.setCardName(dto.getCardName());
        pagamento.setEmailUsuario(usuario.getEmail());
        pagamento.setDataExpiracao(LocalDateTime.now().plusHours(24)); // Expira em 24h
        pagamento.setConfirmado(false);

        pagamentoRepository.save(pagamento);

        // Enviar email de confirmação
        try {
            enviarEmailConfirmacao(usuario.getEmail(), usuario.getNome(), token, dto.getMetodo());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar email de confirmação: " + e.getMessage());
        }

        // Retornar resposta indicando que email foi enviado
        PagamentoDTO resposta = new PagamentoDTO();
        resposta.setMetodo(dto.getMetodo());

        return resposta;
    }

    @Transactional
    public boolean confirmarPagamento(String token) {
        Pagamento pagamento = pagamentoRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token de pagamento inválido"));

        if (pagamento.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token de confirmação expirado");
        }

        if (pagamento.getConfirmado()) {
            throw new RuntimeException("Pagamento já confirmado anteriormente");
        }

        // Confirmar pagamento
        pagamento.setConfirmado(true);
        pagamentoRepository.save(pagamento);

        // Processar pagamento definitivamente
        processarPagamentoDefinitivo(pagamento);

        return true;
    }

    private void validarDadosPagamento(PagamentoDTO dto) {
        if ("pix".equalsIgnoreCase(dto.getMetodo())) {
            // PIX não precisa de validações extras
            return;
        }

        if ("credit-card".equalsIgnoreCase(dto.getMetodo()) || "debit-card".equalsIgnoreCase(dto.getMetodo())) {
            if (dto.getCardNumber() == null || dto.getCardNumber().length() < 12) {
                throw new RuntimeException("Número de cartão inválido");
            }
            if (dto.getCardExpiry() == null || dto.getCardExpiry().isEmpty()) {
                throw new RuntimeException("Data de validade do cartão é obrigatória");
            }
            if (dto.getCardCVV() == null || dto.getCardCVV().length() < 3) {
                throw new RuntimeException("CVV inválido");
            }
            if (dto.getCardName() == null || dto.getCardName().trim().isEmpty()) {
                throw new RuntimeException("Nome no cartão é obrigatório");
            }
        } else {
            throw new RuntimeException("Método de pagamento inválido");
        }
    }

    private void enviarEmailConfirmacao(String email, String nomeUsuario, String token, String metodo) throws IOException {
        String linkConfirmacao = baseUrl + "/pagamento/confirmar/" + token;

        String subject = "Confirmação de Pagamento - VocêAluga";
        String body = String.format(
                "Olá %s!\n\n" +
                        "Recebemos sua solicitação de pagamento via %s.\n\n" +
                        "Para confirmar e processar seu pagamento, clique no link abaixo:\n" +
                        "%s\n\n" +
                        "Este link expira em 24 horas.\n\n" +
                        "Se você não fez esta solicitação, ignore este email.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe VocêAluga",
                nomeUsuario,
                metodo.toUpperCase(),
                linkConfirmacao
        );

        emailService.sendEmail(email, subject, body);
    }

    private void processarPagamentoDefinitivo(Pagamento pagamento) {
        // Aqui você integraria com o gateway de pagamento real
        if ("pix".equalsIgnoreCase(pagamento.getMetodo())) {
            System.out.println("Processando pagamento PIX confirmado");
        } else {
            System.out.println("Processando pagamento com cartão confirmado");
        }

        // Lógica adicional: atualizar status da reserva, etc.
    }

    private String mascaraCartao(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.length() < 8) {
            return "****";
        }
        return "**** **** **** " + numeroCartao.substring(numeroCartao.length() - 4);
    }
}