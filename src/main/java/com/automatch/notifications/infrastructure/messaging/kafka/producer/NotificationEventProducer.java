package com.automatch.notifications.infrastructure.messaging.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishNotificationCreated(String payload) {
        kafkaTemplate.send("notification.created", payload);
    }
}