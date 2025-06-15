package ru.project.iakov.homework2.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.project.iakov.homework2.dto.UserEvent;
import ru.project.iakov.homework2.service.EmailService;

@Component
@Slf4j
public class KafkaConsumerService {

    private final EmailService emailService;

    public KafkaConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void listen(UserEvent event) {
        log.info("Получено событие из Kafka: {}", event);
        String email = event.getEmail();
        String subject = event.getSubject();
        if (event == null || event.getEmail() == null || event.getEventType() == null || event.getSubject() == null) {
            log.warn("Пропущено сообщение: один из параметров null");
            return;
        }

        String text = switch (event.getEventType()) {
            case "CREATED" -> "Здравствуйте! Ваш аккаунт был создан.";
            case "DELETED" -> "Здравствуйте! Ваш аккаунт был удалён.";
            default -> {
                log.warn("Неизвестный тип события: {}", event.getEventType());
                yield "Неизвестное событие.";
            }
        };

        try {
            emailService.sendEmail(email, subject, text);
            log.info("Email успешно отправлен на {}", event.getEmail());
        } catch (Exception e) {
            log.error("Ошибка при отправке email на {}: {}", event.getEmail(), e.getMessage(), e);
        }
    }
}