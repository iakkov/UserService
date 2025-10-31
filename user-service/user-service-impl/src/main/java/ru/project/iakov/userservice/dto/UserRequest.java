package ru.project.iakov.userservice.dto;

/**
 * DTO to send a request to service.
 *
 * @param name
 * @param email
 * @param age
 *
 * @author Iakov Lysenko
 */
public record UserRequest (
        String name,
        String email,
        Integer age
) {
}
