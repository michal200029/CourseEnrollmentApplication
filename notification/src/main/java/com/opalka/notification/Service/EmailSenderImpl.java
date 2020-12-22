package com.opalka.notification.Service;

import com.opalka.notification.dto.EmailClass;
import com.opalka.notification.dto.NotificationInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderImpl implements EmailSender {
    private final JavaMailSender javaMailSender;

    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendEmailsAsNotification(NotificationInfoDTO notificationInfo) {
        String title = "Pamiętaj o kursie!" + notificationInfo.getCourseName();
        StringBuilder content = new StringBuilder();
        content.append("Kurs ");
        content.append(notificationInfo.getCourseName());
        content.append(" rozpoczyna sie ");
        content.append(notificationInfo.getCourseStartDate().toLocalDate());
        content.append(" o godzinie ");
        content.append(notificationInfo.getCourseStartDate().getHour()+":"+notificationInfo.getCourseStartDate().getMinute());
        content.append("\n Opis kursu: ");
        content.append(notificationInfo.getCourseDescription());
        notificationInfo.getEmailsList().forEach(email-> {
            try {
                sendEmail(email,title,content.toString());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendEmail(EmailClass email) throws MessagingException{
        sendEmail(email.getTo(),email.getTitle(),email.getContent());
    }

    private void sendEmail(String to, String title,String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content,false); //false-> czy kod html czy nie
        javaMailSender.send(mimeMessage);
    }
}
