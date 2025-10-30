// package com.automatch.notifications.infrastructure.consumer;

// import com.automatch.notifications.application.dto.NotificationRequestDTO;
// import
// com.automatch.notifications.application.handler.NotificationCommandHandler;
// import com.automatch.notifications.domain.model.NotificationType;
// import com.automatch.notifications.shared.constants.Topics;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.kafka.clients.consumer.ConsumerRecord;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.stereotype.Component;

// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class KafkaNotificationConsumer {

// private final NotificationCommandHandler handler;

// // Ejemplo simple: cuando llega un pago confirmado, notificamos por SMS
// @KafkaListener(topics = Topics.PAYMENT_CONFIRMED)
// public void onPaymentConfirmed(@Payload String payload, ConsumerRecord<?, ?>
// record) {
// log.info("Event received [{}]: {}", record.topic(), payload);

// // Aquí parsearías el JSON (idUsuario, teléfono, etc.). Simplificado:
// var dto = new NotificationRequestDTO(
// NotificationType.SMS,
// "+51999999999", // <- vendrá del evento real
// "Tu pago ha sido confirmado. ¡Gracias por usar AutoMatch!",
// record.topic());
// handler.handle(dto);
// }
// }