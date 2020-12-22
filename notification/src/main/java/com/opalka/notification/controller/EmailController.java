package com.opalka.notification.controller;

import com.opalka.notification.Service.EmailSender;
import com.opalka.notification.dto.EmailClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class EmailController {
    private final EmailSender emailSender;

    public EmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailClass emial){
        try {
             emailSender.sendEmail(emial);
        }catch (MessagingException exp){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Nie udało się wysłać wiadomość do "+emial.getTo());
        }
        return ResponseEntity.ok("Wysłano wiadomość do "+emial.getTo());
    }
}
