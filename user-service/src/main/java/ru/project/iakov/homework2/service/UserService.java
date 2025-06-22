package ru.project.iakov.homework2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.iakov.homework2.dto.UserDto;
import ru.project.iakov.homework2.dto.UserEvent;
import ru.project.iakov.homework2.entity.User;
import ru.project.iakov.homework2.mapper.UserMapper;
import ru.project.iakov.homework2.model.EventType;
import ru.project.iakov.homework2.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaProducerService kafkaProducer;
    private final ObjectMapper objectMapper;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);

        sendKafkaEvent(savedUser.getEmail(), EventType.CREATED);

        return userMapper.toDto(savedUser);
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return userMapper.toDto(user);
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        userRepository.deleteById(id);

        sendKafkaEvent(user.getEmail(), EventType.DELETED);
    }

    private void sendKafkaEvent(String email, EventType eventType) {
        try {
            UserEvent event = new UserEvent(email, eventType);
            String json = objectMapper.writeValueAsString(event);

            kafkaProducer.sendUserEvent(json);
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка сериализации события Kafka: " + e.getMessage());
        }
    }
}