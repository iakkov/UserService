package ru.project.iakov.homework2.model;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.project.iakov.homework2.controller.UserController;
import ru.project.iakov.homework2.dto.UserDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto dto) {
        UserModel model = new UserModel(dto.getId(), dto.getName(), dto.getEmail(), dto.getAge());

        model.add(linkTo(methodOn(UserController.class).getUserById(dto.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
        model.add(linkTo(methodOn(UserController.class).deleteUser(dto.getId())).withRel("delete"));

        return model;
    }
}