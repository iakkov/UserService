package ru.project.iakov.userservice.dto;

import lombok.Builder;

/**
 * DTO to send a request to service.
 *
 * @param name
 * @param email
 * @param age
 *
 * @author Iakov Lysenko
 */
@Builder
public record UserRequest (
        String name,
        String email,
        Integer age
) {
}
