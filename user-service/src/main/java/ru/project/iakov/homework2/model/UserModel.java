package ru.project.iakov.homework2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String name;
    private String email;
    private int age;
}
