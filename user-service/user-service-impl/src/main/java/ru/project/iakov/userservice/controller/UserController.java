package ru.project.iakov.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.project.iakov.userservice.dto.UserRequest;
import ru.project.iakov.userservice.dto.UserResponse;

import java.util.List;
import java.util.UUID;

/**
 * Interface for Swagger annotations.
 *
 * @author Iakov Lysenko
 */
@Tag(name = "UserController",
        description = "Controller for user manipulations")
@RequestMapping("/users")
public interface UserController {

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") UUID id);

    @Operation(summary = "Get all users")
    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers();

    @Operation(summary = "Create user")
    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest);

    @Operation(summary = "Update user information")
    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUser(@PathVariable("id") UUID id,
                                            @RequestBody UserRequest userRequest);

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id);



}
