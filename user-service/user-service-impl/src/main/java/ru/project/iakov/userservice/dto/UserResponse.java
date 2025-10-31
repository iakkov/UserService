package ru.project.iakov.userservice.dto;

import java.util.UUID;

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
public record UserResponse(
        UUID id,
        String name,
        String email,
        int age
) {
}
