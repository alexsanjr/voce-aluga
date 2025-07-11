package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.AuthResponse;
import com.cefet.vocealuga.dtos.LoginRequest;
import com.cefet.vocealuga.entities.Administrador;
import com.cefet.vocealuga.entities.Funcionario;
import com.cefet.vocealuga.entities.Gerente;
import com.cefet.vocealuga.entities.Usuario;
import com.cefet.vocealuga.services.JwtTokenService;
import com.cefet.vocealuga.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        Usuario user = usuarioService.findByEmail(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            String tipo = user instanceof Gerente ? "ROLE_GERENTE" :
                    user instanceof Administrador ? "ROLE_ADMININISTRADOR"   :
                            user instanceof Funcionario ? "ROLE_FUNCIONARIO" :
                                    "ROLE_CLIENTE";


            String token = jwtTokenService.generateToken(user.getEmail(), tipo);
            return ResponseEntity.ok(new AuthResponse(token, tipo, "Login realizado com sucesso"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Credenciais inválidas"));
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication auth) {
        return ResponseEntity.ok("Usuário logado: " + auth.getName());
    }

}

