package ru.project.iakov.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.project.iakov.notificationservice.dto.UserEvent;
import ru.project.iakov.notificationservice.model.EventType;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void listen(String message) {
        log.info("Kafka message: {}", message);
        try {
            UserEvent event = objectMapper.readValue(message, UserEvent.class);
            log.info("Получено сообщение из Kafka: {}", event);
            emailService.sendEmail(event.getEmail(), "Уведомление", generateText(event.getEventType()));
        } catch (Exception e) {
            log.error("Ошибка обработки сообщения: {}", e.getMessage(), e);
        }
    }

    private String generateText(EventType eventType) {
        return switch (eventType) {
            case CREATED -> "Здравствуйте! Ваш аккаунт был создан.";
            case DELETED -> "Здравствуйте! Ваш аккаунт был удалён.";
        };
    }
}