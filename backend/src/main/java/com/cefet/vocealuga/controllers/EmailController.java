package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    @PreAuthorize("hasAnyRole('ROLE_FUNCIONARIO', 'ROLE_GERENTE', 'ROLE_ADMIN', 'ROLE_CLIENTE')")
    public String sendEmail(@RequestParam String to) throws IOException {
        emailService.sendEmail(to, "Assunto de Teste", "Olá! Este é um e-mail de teste.");
        return "Email enviado com sucesso!";
    }
}