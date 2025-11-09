package com.automatch.notifications.infrastructure.messaging.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String NOTIFICATION_CREATED_TOPIC = "notification.created";

    @Bean
    public NewTopic notificationCreatedTopic() {
        return TopicBuilder
                .name(NOTIFICATION_CREATED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}