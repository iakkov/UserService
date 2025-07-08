package ru.project.iakov.homework2.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @Getter
    @Setter
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private Integer age;
    private LocalDateTime createdAt;
}