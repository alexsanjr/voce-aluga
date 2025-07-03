package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.EstacaoDeServicoDTO;
import com.cefet.vocealuga.dtos.EstoqueDTO;
import com.cefet.vocealuga.services.EstacaoDeServicoService;
import com.cefet.vocealuga.services.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/estacao-de-servico")
public class EstacaoDeServicoController {

    @Autowired
    private EstacaoDeServicoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<EstacaoDeServicoDTO> findById(@PathVariable Long id) {
        EstacaoDeServicoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<EstacaoDeServicoDTO>> findAll(Pageable pageable) {
        Page<EstacaoDeServicoDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EstacaoDeServicoDTO> insert(@Valid @RequestBody EstacaoDeServicoDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EstacaoDeServicoDTO> update(@PathVariable Long id, @Valid @RequestBody EstacaoDeServicoDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
