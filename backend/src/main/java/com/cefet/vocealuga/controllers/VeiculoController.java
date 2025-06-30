package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.VeiculoDTO;
import com.cefet.vocealuga.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoDTO> findById(@PathVariable Long id) {
        VeiculoDTO dto = veiculoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<VeiculoDTO>> findAll(
            @RequestParam(name = "marca", defaultValue = "") String marca,
            Pageable pageable) {
        Page<VeiculoDTO> dto = veiculoService.findAll(marca, pageable);
        return ResponseEntity.ok(dto);
    }

}
