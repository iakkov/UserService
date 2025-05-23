package ru.project.iakov.homework2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users", schema = "public")
public class User {
    @Id
    private long id;
    private String name;
    private String email;
    private int age;
    private LocalDateTime createdAt;
}
