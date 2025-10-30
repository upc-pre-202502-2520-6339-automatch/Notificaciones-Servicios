package com.automatch.notifications.domain.service;

import com.automatch.notifications.domain.model.Notification;

public interface NotificationDomainService {
    Notification preValidate(Notification notification);

    Notification markAsSent(Notification notification, String externalId);

    Notification markAsFailed(Notification notification, String reason);

    String renderMessage(String templateKey, String payloadJson);
}
