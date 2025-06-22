package ru.project.iakov.homework2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.project.iakov.homework2.model.EventType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEvent {
    private String email;
    private EventType eventType;
}