package com.automatch.notifications.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para enviar una notificación")
public record NotificationRequestDTO(
        @Schema(example = "SMS", description = "Tipo de notificación (SMS, WHATSAPP, EMAIL)") String type,

        @Schema(example = "+51999999999", description = "Número de teléfono del destinatario en formato internacional") String recipient,

        @Schema(example = "Hola Daniel, esta es una notificación de prueba del microservicio de notificaciones", description = "Mensaje de texto a enviar") String message,

        @Schema(example = "PAYMENT_CONFIRMED", description = "Evento que origina la notificación") String sourceEvent) {
}