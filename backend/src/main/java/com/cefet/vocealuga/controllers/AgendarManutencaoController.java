package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.AgendarManutencaoDTO;
import com.cefet.vocealuga.services.AgendarManutencaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/agendar-manutencao")
public class AgendarManutencaoController {

    @Autowired
    private AgendarManutencaoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AgendarManutencaoDTO> findById(@PathVariable Long id) {
        AgendarManutencaoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<AgendarManutencaoDTO>> findAll(Pageable pageable) {
        Page<AgendarManutencaoDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AgendarManutencaoDTO> insert(@Valid @RequestBody AgendarManutencaoDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AgendarManutencaoDTO> update(@PathVariable Long id, @Valid @RequestBody AgendarManutencaoDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
