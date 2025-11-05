package ru.project.iakov.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.iakov.userservice.domain.entity.User;
import ru.project.iakov.userservice.domain.repository.UserRepository;
import ru.project.iakov.userservice.dto.UserEvent;
import ru.project.iakov.userservice.dto.UserRequest;
import ru.project.iakov.userservice.dto.UserResponse;
import ru.project.iakov.userservice.kafka.KafkaSender;
import ru.project.iakov.userservice.mapper.UserMapper;
import ru.project.iakov.userservice.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * UserServiceImpl for implementing UserService
 * 
 * @author Iakov Lysenko
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaSender kafkaSender;

    @Override
    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> findAll() {
        log.info("Finding all users");
        List<User> users = userRepository.findAll();
        
        List<UserResponse> userResponses = users.stream()
                .map(user -> {
                    UserEvent event = UserEvent.retrieved(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getAge()
                    );
                    kafkaSender.sendUserEvent(user.getId().toString(), event);
                    log.info("User retrieved and event sent to Kafka: userId={}, name={}", user.getId(), user.getName());
                    return userMapper.toResponse(user);
                })
                .collect(Collectors.toList());
        
        log.info("All users retrieved and events sent to Kafka: totalUsers={}", userResponses.size());
        return userResponses;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.info("New user created: name={}, email={}", userRequest.name(), userRequest.email());
        User entity = userMapper.toEntity(userRequest);
        User savedUser = userRepository.save(entity);
        
        UserEvent event = UserEvent.created(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getAge()
        );
        kafkaSender.sendUserEvent(savedUser.getId().toString(), event);
        log.info("User created and event sent to Kafka: userId={}", savedUser.getId());
        
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        log.info("User updated: userId={}, name={}, email={}", id, userRequest.name(), userRequest.email());
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        existingUser.setName(userRequest.name());
        existingUser.setEmail(userRequest.email());
        existingUser.setAge(userRequest.age());

        User updatedUser = userRepository.save(existingUser);
        
        UserEvent event = UserEvent.updated(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getAge()
        );
        kafkaSender.sendUserEvent(updatedUser.getId().toString(), event);
        log.info("User updated and event sent to Kafka: userId={}", updatedUser.getId());
        
        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        log.info("User deleted: userId={}", id);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        
        UserEvent event = UserEvent.deleted(id);
        kafkaSender.sendUserEvent(id.toString(), event);
        
        userRepository.deleteById(id);
        log.info("User deleted and event sent to Kafka: userId={}", id);
    }

}
