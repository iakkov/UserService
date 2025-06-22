package ru.project.iakov.homework2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.iakov.homework2.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}