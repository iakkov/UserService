package ru.project.iakov.userservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * KafkaSender for sending user events to Kafka
 * 
 * @author Iakov Lysenko
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String USER_EVENTS_TOPIC = "user-events";

    public void sendUserEvent(String key, Object event) {
        log.info("Sending event to Kafka: topic={}, key={}, event={}", USER_EVENTS_TOPIC, key, event);

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(USER_EVENTS_TOPIC, key, event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent to Kafka: topic={}, partition={}, offset={}, key={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        key);
            } else {
                log.error("Error sending message to Kafka: key={}, error={}", key, ex.getMessage(), ex);
                throw new RuntimeException("Error sending message to Kafka", ex);
            }
        });
    }

    public void sendUserEvent(Object event) {
        sendUserEvent(null, event);
    }
}
