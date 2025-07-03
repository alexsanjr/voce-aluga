package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.FilialDTO;
import com.cefet.vocealuga.services.FilialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/filiais")
public class FilialController {

    @Autowired
    private FilialService filialService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<FilialDTO> findById(@PathVariable Long id) {
        FilialDTO dto = filialService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<FilialDTO>> findAll(Pageable pageable) {
        Page<FilialDTO> dto = filialService.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<FilialDTO> insert(@Valid @RequestBody FilialDTO dto) {
        dto = filialService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FilialDTO> update(@PathVariable Long id, @Valid @RequestBody FilialDTO dto) {
        dto = filialService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filialService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
