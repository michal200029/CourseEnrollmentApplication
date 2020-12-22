package com.opalka.notification.Service;

import com.opalka.notification.dto.NotificationInfoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqListener {

    private final RabbitTemplate rabbitTemplate;
    private final EmailSender emailSender;

    public RabbitMqListener(RabbitTemplate rabbitTemplate, EmailSender emailSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "enrollFinish")
    public void finishEnrollHandler(NotificationInfoDTO notificationInfo){
        emailSender.sendEmailsAsNotification(notificationInfo);
    }


}
