package ru.project.iakov.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.iakov.notificationservice.dto.EmailRequest;
import ru.project.iakov.notificationservice.service.EmailService;


@Slf4j
@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            String body = switch (request.getEventType()) {
                case CREATED -> "Здравствуйте! Ваш аккаунт был создан.";
                case DELETED -> "Здравствуйте! Ваш аккаунт был удалён.";
            };

            emailService.sendEmail(request.getEmail(), request.getSubject(), body);
            return ResponseEntity.ok("Письмо отправлено");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Ошибка: " + e.getMessage());
        }
    }
}
