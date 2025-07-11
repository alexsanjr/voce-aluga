package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.FilialDTO;
import com.cefet.vocealuga.dtos.PagamentoDTO;
import com.cefet.vocealuga.services.FilialService;
import com.cefet.vocealuga.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> insert(@Valid @RequestBody PagamentoDTO dto) {
        PagamentoDTO resposta = pagamentoService.processarPagamento(dto);
        return ResponseEntity.ok(resposta); // HTTP 200 OK com DTO no corpo
    }
}
