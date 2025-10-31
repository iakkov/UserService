package ru.project.iakov.userservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface for Swagger annotations.
 *
 * @author Iakov Lysenko
 */
@Tag(name = "UserController",
        description = "Controller for user manipulations")
@RequestMapping("/users")
public interface UserController {

}
