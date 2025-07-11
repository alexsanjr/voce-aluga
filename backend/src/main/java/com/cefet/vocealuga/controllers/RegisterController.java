package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.LoginRequest;
import com.cefet.vocealuga.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        if (userService.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        userService.saveUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
