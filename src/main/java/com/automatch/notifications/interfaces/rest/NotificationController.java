package com.automatch.notifications.interfaces.rest;

import com.automatch.notifications.application.dto.NotificationRequestDTO;
import com.automatch.notifications.domain.model.Notification;
import com.automatch.notifications.domain.model.NotificationStatus;
import com.automatch.notifications.infrastructure.services.TwilioNotificationService;
import com.automatch.notifications.infrastructure.repository.JpaNotificationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification Controller", description = "Gestión de notificaciones SMS (mock y reales)")
public class NotificationController {

    private final JpaNotificationRepository notificationRepository;
    private final TwilioNotificationService twilioNotificationService;

    public NotificationController(JpaNotificationRepository notificationRepository,
            TwilioNotificationService twilioNotificationService) {
        this.notificationRepository = notificationRepository;
        this.twilioNotificationService = twilioNotificationService;
    }

    // Crear y enviar notificación
    @PostMapping("/send")
    @Operation(summary = "Enviar una nueva notificación", description = "Permite enviar notificaciones por SMS, WhatsApp o Email.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "Ejemplo de envío SMS", value = """
                    {
                      "type": "SMS",
                      "recipient": "+51999999999",
                      "message": "Hola Daniel, esta es una notificación de prueba del microservicio de notificaciones",
                      "sourceEvent": "PAYMENT_CONFIRMED"
                    }
                    """)
    })))
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationRequestDTO request) {
        Notification notification = twilioNotificationService.send(request);
        return ResponseEntity.ok(notification);
    }

    // Obtener todas las notificaciones
    @Operation(summary = "Listar todas las notificaciones")
    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    // Obtener una notificación por ID
    @Operation(summary = "Obtener notificación por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar una notificación por ID
    @Operation(summary = "Eliminar notificación por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!notificationRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        notificationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar notificaciones filtrando por tipo, estado o destinatario")
    public ResponseEntity<List<Notification>> searchNotifications(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) NotificationStatus status,
            @RequestParam(required = false) String recipient) {

        List<Notification> results = notificationRepository.search(type, status, recipient);
        return ResponseEntity.ok(results);
    }
}