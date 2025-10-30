package com.automatch.notifications.application.service.impl;

import com.automatch.notifications.application.dto.NotificationRequestDTO;
import com.automatch.notifications.application.service.NotificationApplicationService;
import com.automatch.notifications.domain.model.Notification;
import com.automatch.notifications.domain.model.NotificationStatus;
import com.automatch.notifications.domain.model.NotificationType;
import com.automatch.notifications.domain.repository.NotificationRepository;
import com.automatch.notifications.domain.service.NotificationDomainService;
import com.automatch.notifications.infrastructure.messaging.TwilioNotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationApplicationServiceImpl implements NotificationApplicationService {

    private final NotificationRepository repository;
    private final NotificationDomainService domainService;
    private final TwilioNotificationSender sender;

    @Override
    public Notification send(NotificationRequestDTO request) {

        NotificationType type;
        try {
            type = NotificationType.valueOf(request.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de notificación inválido: " + request.type());
        }

        Notification notification = Notification.builder()
                .type(type)
                .recipient(request.recipient())
                .message(request.message())
                .status(NotificationStatus.PENDING)
                .sourceEvent(request.sourceEvent() != null ? request.sourceEvent() : "manual")
                .createdAt(OffsetDateTime.now())
                .build();

        // Reglas de negocio puras
        notification = domainService.preValidate(notification);

        try {
            sender.send(notification);
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(OffsetDateTime.now());
        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setError(ex.getMessage());
        }
        return repository.save(notification);
    }

    @Override
    public List<Notification> list() {
        return repository.findAll();
    }

    @Override
    public Notification get(Long id) {
        return repository.findById(id).orElseThrow();
    }
}