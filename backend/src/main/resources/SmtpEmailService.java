package com.cefet.vocealuga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

@Service
public class SmtpEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${MAIL_FROM}")
    private String fromEmail;

    @Value("${mail.from.name}")
    private String fromName;

    @Async
    public CompletableFuture<Void> sendEmailAsync(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setReplyTo(fromEmail);

            // Headers para melhorar deliverability
            message.addHeader("X-Mailer", "VocêAluga System");
            message.addHeader("X-Priority", "3");
            message.addHeader("List-Unsubscribe", "<mailto:" + fromEmail + ">");

            mailSender.send(message);
            System.out.println("Email SMTP enviado com sucesso para: " + to);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email SMTP: " + e.getMessage());
            throw new RuntimeException("Falha ao enviar email SMTP", e);
        }
    }

    public void sendTestEmail(String to) {
        String subject = "✅ Teste SMTP - VocêAluga";
        String body = createHtmlTestEmail();
        sendEmailAsync(to, subject, body);
    }

    private String createHtmlTestEmail() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background: #f9f9f9; }
                    .footer { padding: 10px; text-align: center; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🚗 VocêAluga</h1>
                        <p>Teste de Email SMTP</p>
                    </div>
                    <div class="content">
                        <h2>Email funcionando perfeitamente!</h2>
                        <p>Se você recebeu esta mensagem, o serviço SMTP está configurado corretamente.</p>
                        <p><strong>Data/Hora:</strong> %s</p>
                        <p><strong>Servidor:</strong> Gmail SMTP</p>
                        <p><strong>Status:</strong> ✅ Ativo</p>
                    </div>
                    <div class="footer">
                        <p>Este é um email automático do sistema VocêAluga</p>
                        <p>Não responda este email</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(java.time.LocalDateTime.now().toString());
    }
}