package com.automatch.notifications.infrastructure.repository;

import com.automatch.notifications.domain.model.Notification;
import com.automatch.notifications.domain.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaNotificationRepository extends JpaRepository<Notification, Long> {

    // Buscar por estado (SENT, FAILED, PENDING)
    List<Notification> findByStatus(NotificationStatus status);

    // Buscar por tipo (SMS, WHATSAPP, EMAIL)
    List<Notification> findByType(String type);

    // Buscar por destinatario (ej: "+51999999999")
    List<Notification> findByRecipient(String recipient);

    // Búsqueda combinada (parámetros opcionales)
    @Query("""
            SELECT n FROM Notification n
            WHERE (:type IS NULL OR LOWER(n.type) = LOWER(:type))
              AND (:status IS NULL OR n.status = :status)
              AND (:recipient IS NULL OR n.recipient = :recipient)
            ORDER BY n.createdAt DESC
            """)
    List<Notification> search(
            @Param("type") String type,
            @Param("status") NotificationStatus status,
            @Param("recipient") String recipient);
}