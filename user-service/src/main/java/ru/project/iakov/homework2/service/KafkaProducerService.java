package ru.project.iakov.homework2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public void sendUserEvent(String message) {
        log.info("Отправка сообщения в Kafka: {}", message);
        kafkaTemplate.send(TOPIC, message);
    }
}