package com.cefet.vocealuga.controllers;

import com.cefet.vocealuga.services.EmailService;
import com.cefet.vocealuga.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmtpEmailService smtpEmailService;

    @GetMapping("/send-email")
    @PreAuthorize("hasAnyRole('ROLE_FUNCIONARIO', 'ROLE_GERENTE', 'ROLE_ADMIN', 'ROLE_CLIENTE')")
    public String sendEmail(@RequestParam String to) throws IOException {
        emailService.sendEmail(to, "Assunto de Teste", "Olá! Este é um e-mail de teste.");
        return "Email enviado com sucesso!";
    }

    @GetMapping("/test-email")
    public ResponseEntity<Map<String, String>> testSmtpEmail(@RequestParam String to) {
        try {
            smtpEmailService.sendTestEmail(to);
            return ResponseEntity.ok(Map.of(
                    "status", "sucesso",
                    "mensagem", "Email sendo enviado de forma assíncrona para: " + to,
                    "tipo", "SMTP",
                    "observacao", "O email chegará em alguns segundos"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "erro",
                    "mensagem", "Falha ao enviar email: " + e.getMessage(),
                    "tipo", "SMTP"
            ));
        }
    }
}