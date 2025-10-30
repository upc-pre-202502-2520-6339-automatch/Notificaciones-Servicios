// package com.automatch.notifications.infrastructure.config;

// import org.apache.kafka.clients.admin.NewTopic;
// import
// org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class KafkaConfig {

// // Crea el topic si el broker permite auto-creation
// @Bean
// @ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
// public NewTopic paymentsConfirmedTopic() {
// return new NewTopic("payments.confirmed", 1, (short) 1);
// }
// }