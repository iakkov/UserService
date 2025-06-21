package ru.project.iakov.homework2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.iakov.homework2.dto.UserEvent;
import ru.project.iakov.homework2.service.KafkaProducerService;

@Slf4j
@RestController
@RequestMapping("/api/v1/kafka")
@Validated
public class NotificationController {
    private final KafkaProducerService kafkaProducer;

    public NotificationController(KafkaProducerService kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> send(@RequestBody UserEvent request) {
        log.info("Получен запрос на публикацию события: {}", request);
        kafkaProducer.sendUserEvent(request);
        return ResponseEntity.ok().build();
    }
}