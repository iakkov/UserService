package ru.project.iakov.homework2;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private int age;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public User() {
    }
    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }

    public long getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public int getAge() {return age;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setAge(int age) {this.age = age;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    @Override
    public String toString() {
        return "User{id=%d, name='%s', email='%s', age=%d, createdAt=%s}".formatted(
                id, name, email, age, createdAt);
    }
}
