package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.LoginRequest;
import com.cefet.vocealuga.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UsuarioService userService;

    public RegisterController(UsuarioService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        if (userService.findByUsername(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        userService.saveUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
