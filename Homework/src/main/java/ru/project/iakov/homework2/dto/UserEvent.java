package ru.project.iakov.homework2.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {
    private String email;
    private String eventType;
}
