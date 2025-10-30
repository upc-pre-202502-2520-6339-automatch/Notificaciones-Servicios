package com.automatch.notifications.application.service;

import com.automatch.notifications.application.dto.NotificationRequestDTO;
import com.automatch.notifications.domain.model.Notification;
import java.util.List;

public interface NotificationApplicationService {
    Notification send(NotificationRequestDTO request);

    List<Notification> list();

    Notification get(Long id);
}