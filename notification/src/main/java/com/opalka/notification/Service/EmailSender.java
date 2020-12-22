package com.opalka.notification.Service;

import com.opalka.notification.dto.EmailClass;
import com.opalka.notification.dto.NotificationInfoDTO;

import javax.mail.MessagingException;


public interface EmailSender {
    void sendEmailsAsNotification(NotificationInfoDTO notificationInfo);
    void sendEmail(EmailClass email) throws MessagingException;
}
