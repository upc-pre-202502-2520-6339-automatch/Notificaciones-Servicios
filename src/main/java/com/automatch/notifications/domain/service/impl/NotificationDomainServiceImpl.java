package com.automatch.notifications.domain.service.impl;

import com.automatch.notifications.domain.model.Notification;
import com.automatch.notifications.domain.model.NotificationStatus;
import com.automatch.notifications.domain.service.NotificationDomainService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class NotificationDomainServiceImpl implements NotificationDomainService {

    @Override
    public Notification preValidate(Notification notification) {
        if (notification.getRecipient() == null || notification.getRecipient().isBlank()) {
            throw new IllegalArgumentException("El destinatario no puede ser nulo o vacío.");
        }

        if (notification.getMessage() == null || notification.getMessage().isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo o vacío.");
        }

        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(OffsetDateTime.now());
        return notification;
    }

    @Override
    public Notification markAsSent(Notification notification, String externalId) {
        notification.setStatus(NotificationStatus.SENT);
        notification.setExternalId(externalId);
        notification.setSentAt(OffsetDateTime.now());
        return notification;
    }

    @Override
    public Notification markAsFailed(Notification notification, String reason) {
        notification.setStatus(NotificationStatus.FAILED);
        notification.setFailedAt(OffsetDateTime.now());
        notification.setError(reason);

        // Corregido: reemplaza setMessageBody por setMessage
        notification.setMessage(
                (notification.getMessage() != null ? notification.getMessage() : "")
                        + " | ERR: " + reason);

        return notification;
    }

    @Override
    public String renderMessage(String templateKey, String payloadJson) {
        // Simplificado, pero extensible a motores de plantillas como Mustache o
        // FreeMarker
        return "[" + templateKey + "] -> " + payloadJson;
    }
}