package ru.project.iakov.userservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.project.iakov.userservice.dto.UserEvent;

/**
 * KafkaConsumer for consuming user events from Kafka
 * 
 * @author Iakov Lysenko
 */
@Slf4j
@Component
public class KafkaConsumer {

    private static final String USER_EVENTS_TOPIC = "user-events";

    @KafkaListener(topics = USER_EVENTS_TOPIC, groupId = "user-service-group")
    public void consumeUserEvent(
            @Payload UserEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.RECEIVED_KEY) String key) {

        log.info("New message received from Kafka: topic={}, partition={}, offset={}, key={}, event={}",
                topic, partition, offset, key, event);

        try {
            String eventType = event.eventType();
            log.info("Event processing: type={}", eventType);

            switch (eventType) {
                case "USER_CREATED" -> {
                    log.info("Processing user creation event: userId={}, name={}, email={}",
                            event.userId(), event.name(), event.email());
                }
                case "USER_UPDATED" -> {
                    log.info("Processing user update event: userId={}, name={}, email={}",
                            event.userId(), event.name(), event.email());
                }
                case "USER_DELETED" -> {
                    log.info("Processing user deletion event: userId={}", event.userId());
                }
                case "USER_RETRIEVED" -> {
                    log.info("Processing user retrieval event: userId={}, name={}, email={}",
                            event.userId(), event.name(), event.email());
                }
                default -> {
                    log.warn("Unknown event type: {}", eventType);
                }
            }

            log.info("Event processed: type={}, userId={}", eventType, event.userId());

        } catch (Exception e) {
            log.error("Processing error: event={}, error={}", event, e.getMessage(), e);
        }
    }
}
