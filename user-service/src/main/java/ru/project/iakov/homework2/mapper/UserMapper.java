package ru.project.iakov.homework2.mapper;
import org.springframework.stereotype.Component;
import ru.project.iakov.homework2.entity.User;
import ru.project.iakov.homework2.dto.UserDto;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .build();

    }

    public User toEntity(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}