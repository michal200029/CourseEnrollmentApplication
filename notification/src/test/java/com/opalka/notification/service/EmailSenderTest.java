package com.opalka.notification.service;

import com.opalka.notification.Service.EmailSender;
import com.opalka.notification.dto.EmailClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    EmailSender emailSender;

    @Test
    public void send_emial_test() throws MessagingException {
        EmailClass email = EmailClass.builder()
                .to("michalopalka2@gmail.com")
                .title("LUBIE PIWO")
                .content("WÓDECZKE TAKZE!").build();
        emailSender.sendEmail(email);
    }

}
