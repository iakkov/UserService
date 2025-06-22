package ru.project.iakov.notificationservice.dto;

import lombok.*;
import ru.project.iakov.notificationservice.model.EventType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String email;
    private String subject;
    private EventType eventType;
}