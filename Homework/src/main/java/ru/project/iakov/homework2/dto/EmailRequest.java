package ru.project.iakov.homework2.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String email;
    private String subject;
    private String eventType;
}
