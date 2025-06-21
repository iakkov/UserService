package ru.project.iakov.homework2.dto;

import lombok.*;

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
    private String eventType;
}
