package ru.project.iakov.userservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.iakov.userservice.domain.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
