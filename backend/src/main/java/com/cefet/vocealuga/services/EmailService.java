package com.cefet.vocealuga.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private final SendGrid sendGrid;

    @Value("${SENDGRID_FROM_EMAIL:noreply@vocealuga.com}")
    private String fromEmail;

    @Autowired
    public EmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void sendEmail(String to, String subject, String body) throws IOException {
        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());
        System.out.println("Headers: " + response.getHeaders());
    }

    public void sendWelcomeEmail(String to, String username) throws IOException {
        String subject = "Bem-vindo ao VocêAluga!";
        String body = String.format(
                "Olá %s!\n\n" +
                        "Bem-vindo ao VocêAluga! Sua conta foi criada com sucesso.\n" +
                        "Agora você pode fazer reservas de veículos de forma fácil e rápida.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe VocêAluga",
                username
        );
        sendEmail(to, subject, body);
    }

    public void sendReservationConfirmation(String to, String reservaId, String vehicleModel) throws IOException {
        String subject = "Confirmação de Reserva - VocêAluga";
        String body = String.format(
                "Sua reserva foi confirmada!\n\n" +
                        "ID da Reserva: %s\n" +
                        "Veículo: %s\n\n" +
                        "Obrigado por escolher o VocêAluga!\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe VocêAluga",
                reservaId, vehicleModel
        );
        sendEmail(to, subject, body);
    }
}