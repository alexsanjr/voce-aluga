package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.dtos.RegisterRequest;
import com.cefet.vocealuga.entities.*;
import com.cefet.vocealuga.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UsuarioService userService;

    public RegisterController(UsuarioService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        if (userService.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }
        if (calcularIdade(request.getDataNascimento()) < 18) {
            return ResponseEntity.badRequest().body("Usuário é menor de idade");
        }

        Usuario novoUsuario;

        switch (request.getTipo()) {
            case CLIENTE:
                novoUsuario = new Cliente(null, request.getNome(), request.getDocumento(), request.getDataNascimento(), request.getEmail(), request.getPassword(), request.getTelefone(), 0);
                break;
            case FUNCIONARIO:
                novoUsuario = new Funcionario(null, request.getNome(), request.getDocumento(), request.getDataNascimento(), request.getEmail(), request.getPassword(), request.getTelefone(),"",new Filial());
                break;
            case GERENTE:
                novoUsuario = new Gerente(null, request.getNome(), request.getDocumento(), request.getDataNascimento(), request.getEmail(), request.getPassword(), request.getTelefone(),"",new Filial());
                break;
            case ADMINISTRADOR:
                novoUsuario = new Administrador(null, request.getNome(), request.getDocumento(), request.getDataNascimento(), request.getEmail(), request.getPassword(), request.getTelefone(),"",new Filial());
                break;
            default:
                return ResponseEntity.badRequest().body("Tipo de usuário inválido");
        }

        userService.saveUser(novoUsuario);

        return ResponseEntity.ok("Usuário " + request.getTipo() + " criado com sucesso!");
    }

    public static int calcularIdade(LocalDate nascimento) {
        return Period.between(nascimento, LocalDate.now()).getYears();
    }

}
