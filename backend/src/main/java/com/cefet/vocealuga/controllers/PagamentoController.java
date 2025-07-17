package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.PagamentoDTO;
import com.cefet.vocealuga.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_FUNCIONARIO', 'ROLE_GERENTE', 'ROLE_ADMIN', 'ROLE_CLIENTE')")
    public ResponseEntity<Map<String, String>> insert(@Valid @RequestBody PagamentoDTO dto) {
        pagamentoService.processarPagamento(dto);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Email de confirmação enviado. Verifique sua caixa de entrada.",
                "status", "pendente"
        ));
    }

    @GetMapping("/confirmar/{token}")
    public ResponseEntity<Map<String, String>> confirmarPagamento(@PathVariable String token) {
        try {
            boolean confirmado = pagamentoService.confirmarPagamento(token);
            if (confirmado) {
                return ResponseEntity.ok(Map.of(
                        "mensagem", "Pagamento confirmado com sucesso!",
                        "status", "confirmado"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                        "erro", "Erro ao confirmar pagamento",
                        "status", "erro"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", e.getMessage(),
                    "status", "erro"
            ));
        }
    }
}