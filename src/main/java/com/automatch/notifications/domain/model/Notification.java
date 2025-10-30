package com.automatch.notifications.domain.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private String sourceEvent;

    private String externalId;

    @Column(columnDefinition = "TEXT")
    private String error;

    private OffsetDateTime createdAt;
    private OffsetDateTime sentAt;
    private OffsetDateTime failedAt;
}
