package ru.project.iakov.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.project.iakov.notificationservice.model.EventType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UserEvent {
    private String email;
    private String subject;
    private EventType eventType;
}