package ru.project.iakov.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.iakov.notificationservice.dto.EmailRequest;
import ru.project.iakov.notificationservice.model.EventType;
import ru.project.iakov.notificationservice.service.EmailService;


@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendEmail(request.getEmail(), request.getSubject(), generateText(request.getEventType()));
            return ResponseEntity.ok("Письмо отправлено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при отправке письма: " + e.getMessage());
        }
    }
    private String generateText(EventType eventType) {
        return switch (eventType) {
            case CREATED -> "Здравствуйте! Ваш аккаунт был создан.";
            case DELETED -> "Здравствуйте! Ваш аккаунт был удалён.";
        };
    }
}