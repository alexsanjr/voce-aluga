package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.LoginRequest;
import com.cefet.vocealuga.entities.Usuario;
import com.cefet.vocealuga.services.JwtTokenService;
import com.cefet.vocealuga.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenService jwtTokenService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JwtTokenService jwtTokenService,
                          UsuarioService usuarioService,
                          PasswordEncoder passwordEncoder) {
        this.jwtTokenService = jwtTokenService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Usuario user = usuarioService.findByUsername(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtTokenService.generateToken(user.getEmail());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication auth) {
        return ResponseEntity.ok("Usuário logado: " + auth.getName());
    }

}

