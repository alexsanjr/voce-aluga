package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.MotoristaDTO;
import com.cefet.vocealuga.dtos.MotoristaLogadoDTO;
import com.cefet.vocealuga.services.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService service;

    @PostMapping("/logado")
    @PreAuthorize("hasAnyRole('ROLE_FUNCIONARIO', 'ROLE_GERENTE', 'ROLE_ADMIN', 'ROLE_CLIENTE')")
    public ResponseEntity<MotoristaDTO> criarMotoristaLogado(@RequestBody MotoristaLogadoDTO dto, Authentication authentication) {
        MotoristaDTO motorista = service.criarMotoristaPeloUsuarioLogado(dto.getCnh(), authentication);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(motorista.getId())
                .toUri();

        return ResponseEntity.created(uri).body(motorista);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_FUNCIONARIO', 'ROLE_GERENTE', 'ROLE_ADMIN', 'ROLE_CLIENTE')")
    public ResponseEntity<MotoristaDTO> criarMotorista(@RequestBody MotoristaDTO dto) {
        MotoristaDTO motorista = service.criarMotorista(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(motorista.getId())
                .toUri();

        return ResponseEntity.created(uri).body(motorista);
    }
}