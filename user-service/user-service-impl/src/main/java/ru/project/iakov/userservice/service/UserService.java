package ru.project.iakov.userservice.service;

import ru.project.iakov.userservice.dto.UserRequest;
import ru.project.iakov.userservice.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse findById(UUID id);

    List<UserResponse> findAll();

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(UUID id, UserRequest userRequest);

    void deleteUser(UUID id);

}
