package ru.project.iakov.userservice.mapper;

import org.mapstruct.Mapper;
import ru.project.iakov.userservice.domain.entity.User;
import ru.project.iakov.userservice.dto.UserRequest;
import ru.project.iakov.userservice.dto.UserResponse;

/**
 * UserMapper for mapping User to UserResponse and UserRequest to User
 *  
 * @author Iakov Lysenko
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(UserRequest userRequest);

}
