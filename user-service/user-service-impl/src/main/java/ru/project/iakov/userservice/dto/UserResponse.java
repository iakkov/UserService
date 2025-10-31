package ru.project.iakov.userservice.dto;

import lombok.Builder;

/**
 * DTO to get response with information of User
 * Includes the main information of User
 *
 * @param id
 * @param name
 * @param email
 * @param age
 *
 * @author Iakov Lysenko
 */
@Builder
public record UserResponse(
        Long id,
        String name,
        String email,
        int age
) {
}
