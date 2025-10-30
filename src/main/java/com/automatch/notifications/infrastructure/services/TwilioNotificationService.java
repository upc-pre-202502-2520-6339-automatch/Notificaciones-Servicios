package com.automatch.notifications.infrastructure.services;

import com.automatch.notifications.application.dto.NotificationRequestDTO;
import com.automatch.notifications.domain.model.Notification;
import com.automatch.notifications.domain.model.NotificationStatus;
import com.automatch.notifications.domain.model.NotificationType;
import com.automatch.notifications.infrastructure.repository.JpaNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TwilioNotificationService {

    private final JpaNotificationRepository repository;

    @Value("${twilio.from.phone:+10000000000}")
    private String fromPhone;

    /**
     * Simula el envío de una notificación sin conectarse a Twilio real.
     */
    public Notification send(NotificationRequestDTO request) {
        NotificationType type;

        try {
            type = NotificationType.valueOf(request.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException("Tipo de notificación no soportado: " + request.type());
        }

        Notification notification = Notification.builder()
                .type(type)
                .recipient(request.recipient())
                .message(request.message())
                .status(NotificationStatus.PENDING)
                .sourceEvent(request.sourceEvent() != null ? request.sourceEvent() : "manual")
                .createdAt(OffsetDateTime.now())
                .build();

        try {
            // SIMULACIÓN — sin Twilio real
            switch (type) {
                case SMS -> System.out.println("📱 Simulando envío de SMS a " + notification.getRecipient());
                case WHATSAPP -> System.out.println("💬 Simulando envío de WhatsApp a " + notification.getRecipient());
                case EMAIL -> System.out.println("📧 Simulando envío de Email a " + notification.getRecipient());
                default -> throw new UnsupportedOperationException("Tipo no soportado: " + notification.getType());
            }

            // Simulamos tiempo de envío
            Thread.sleep(500);

            // Éxito simulado
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(OffsetDateTime.now());
            System.out.println("Notificación simulada enviada correctamente.");

        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setError("Error simulado: " + e.getMessage());
            System.err.println("Error al simular envío: " + e.getMessage());
        }

        // Guardamos el resultado (simulado) en BD
        return repository.save(notification);
    }
}