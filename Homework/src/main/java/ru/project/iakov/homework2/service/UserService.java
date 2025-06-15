package ru.project.iakov.homework2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.project.iakov.homework2.dto.UserDto;
import ru.project.iakov.homework2.entity.User;
import ru.project.iakov.homework2.dto.UserEvent;
import ru.project.iakov.homework2.mapper.UserMapper;
import ru.project.iakov.homework2.repository.UserRepository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements Serializable {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private KafkaProducerService kafkaProducer;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, KafkaProducerService kafkaProducer) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.kafkaProducer = kafkaProducer;
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        kafkaProducer.sendUserEvent(
                new UserEvent(
                        savedUser.getEmail(),
                        "Уведомление",
                        "CREATED"));
        return userMapper.toDto(savedUser);
    }
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
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
                .orElseThrow(() -> new IllegalArgumentException());
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.deleteById(id);
        kafkaProducer.sendUserEvent(
                new UserEvent(
                        user.getEmail(),
                        "Уведомление",
                        "DELETED"));
    }
}