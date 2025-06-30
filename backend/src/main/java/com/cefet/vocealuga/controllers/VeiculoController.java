package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.VeiculoDTO;
import com.cefet.vocealuga.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoDTO> findById(@PathVariable Long id) {
        VeiculoDTO dto = veiculoService.findById(id);
        return ResponseEntity.ok(dto);
    }

}
