package ru.project.iakov.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO для событий пользователя в Kafka
 * 
 * @author Iakov Lysenko
 * @param eventType
 * @param userId
 * @param name
 * @param email
 * @param age
 * @param timestamp
 */
public record UserEvent (
        String eventType,
        UUID userId,
        String name,
        String email,
        Integer age,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp
){
    public static UserEvent created(UUID userId, String name, String email, Integer age) {
        return new UserEvent("USER_CREATED", userId, name, email, age, LocalDateTime.now());
    }

    public static UserEvent updated(UUID userId, String name, String email, Integer age) {
        return new UserEvent("USER_UPDATED", userId, name, email, age, LocalDateTime.now());
    }

    public static UserEvent deleted(UUID userId) {
        return new UserEvent("USER_DELETED", userId, null, null, null, LocalDateTime.now());
    }

    public static UserEvent retrieved(UUID userId, String name, String email, Integer age) {
        return new UserEvent("USER_RETRIEVED", userId, name, email, age, LocalDateTime.now());
    }
}
