package ru.project.iakov.homework2.dto;

import lombok.*;
import ru.project.iakov.homework2.model.EventType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {
    @NonNull
    private String email;
    @NonNull
    private String subject;
    @NonNull
    private EventType eventType;
}
