package ru.project.iakov.homework2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.iakov.homework2.dto.UserEvent;
import ru.project.iakov.homework2.service.KafkaProducerService;

@Slf4j
@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
@Validated
public class NotificationController {

    private final KafkaProducerService kafkaProducer;
    private final ObjectMapper objectMapper;

    @PostMapping("/publish")
    public ResponseEntity<Void> send(@RequestBody UserEvent request) {
        log.info("Получен запрос на публикацию события: {}", request);
        try {
            String message = objectMapper.writeValueAsString(request);
            kafkaProducer.sendUserEvent(message);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации события: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}