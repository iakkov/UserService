package ru.project.iakov.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.project.iakov.userservice.domain.entity.User;
import ru.project.iakov.userservice.dto.UserRequest;
import ru.project.iakov.userservice.dto.UserResponse;

/**
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(UserRequest userRequest);

}
