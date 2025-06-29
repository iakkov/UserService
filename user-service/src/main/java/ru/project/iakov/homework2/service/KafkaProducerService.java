package ru.project.iakov.homework2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "user-events";

    @CircuitBreaker(name = "kafkaProducer", fallbackMethod = "sendUserEventFallback")
    public void sendUserEvent(String message) {
        log.info("Отправка сообщения в Kafka: {}", message);
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendUserEventFallback(String message, Throwable throwable) {
        log.error("Ошибка отправки в Kafka: {}", throwable.getMessage());
    }
}