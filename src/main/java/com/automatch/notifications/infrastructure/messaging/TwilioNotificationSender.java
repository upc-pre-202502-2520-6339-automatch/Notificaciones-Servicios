package com.automatch.notifications.infrastructure.messaging;

import com.automatch.notifications.domain.model.Notification;
import com.automatch.notifications.domain.model.NotificationType;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TwilioNotificationSender {

    @Value("${twilio.from.phone:+10000000000}")
    private String fromPhone;

    public void send(Notification notification) {
        NotificationType type = notification.getType();

        switch (type) {
            case SMS -> {
                Message.creator(
                        new PhoneNumber(notification.getRecipient()),
                        new PhoneNumber(fromPhone),
                        notification.getMessage()).create();
            }
            case WHATSAPP -> {
                Message.creator(
                        new PhoneNumber("whatsapp:" + notification.getRecipient()),
                        new PhoneNumber("whatsapp:" + fromPhone),
                        notification.getMessage()).create();
            }
            default -> throw new UnsupportedOperationException(
                    "Tipo no soportado: " + type);
        }
    }
}