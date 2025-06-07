package ru.project.iakov.homework2;

import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.project.iakov.homework2.dao.UserDao;
import ru.project.iakov.homework2.dao.UserDaoImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class SimpleContainerTest {
    private static UserDao userDao;
    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeAll
    static void setUp() {
        postgres.start();
        System.setProperty("DB_URL", postgres.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgres.getUsername());
        System.setProperty("DB_PASSWORD", postgres.getPassword());

        HibernateUtil.rebuildSessionFactoryForTests();
        userDao = new UserDaoImpl();
    }

    @DisplayName("Создание и поиск по ID")
    @Test
    @Order(1)
    void shouldCreateAndFindUserById() {
        User user = User.builder()
                .name("Iakov")
                .email("lysenko_iakov@yahoo.com")
                .age(25)
                .createdAt(LocalDateTime.now())
                .build();

        userDao.create(user);

        assertNotNull(user.getId());

        Optional<User> saved = userDao.findById(user.getId());
        assertTrue(saved.isPresent());
        assertEquals("Iakov", saved.get().getName());
    }

    @DisplayName("Обновление пользователя")
    @Test
    @Order(2)
    void shouldUpdateUser() {
        User user = User.builder()
                .name("Original")
                .email("original@email.com")
                .age(30)
                .createdAt(LocalDateTime.now())
                .build();

        userDao.create(user);
        user.setName("Updated");
        user.setEmail("updated@email.com");

        userDao.update(user);

        Optional<User> updated = userDao.findById(user.getId());
        assertTrue(updated.isPresent());
        assertEquals("Updated", updated.get().getName());
        assertEquals("updated@email.com", updated.get().getEmail());
    }

    @DisplayName("Удаление существующего пользователя")
    @Test
    @Order(3)
    void shouldDeleteUser() {
        User user = User.builder()
                .name("ToDelete")
                .email("delete@example.com")
                .age(40)
                .createdAt(LocalDateTime.now())
                .build();

        userDao.create(user);
        Long id = user.getId();

        userDao.delete(id);

        Optional<User> deleted = userDao.findById(id);
        assertFalse(deleted.isPresent());
    }

    @DisplayName("Поиск по несуществующему ID")
    @Test
    @Order(4)
    void shouldReturnEmptyForNonExistingUser() {
        Optional<User> user = userDao.findById(99999L);
        assertTrue(user.isEmpty());
    }

    @DisplayName("Удаление несуществующего пользователя")
    @Test
    @Order(5)
    void shouldNotThrowWhenDeletingNonExistingUser() {
        assertDoesNotThrow(() -> userDao.delete(99999L));
    }

    @DisplayName("Создание пользователя с null-именем должно выбросить исключение")
    @Test
    @Order(6)
    void shouldThrowWhenCreatingUserWithNullName() {
        User user = User.builder()
                .email("nullname@example.com")
                .age(30)
                .createdAt(LocalDateTime.now())
                .build();

        assertThrows(PersistenceException.class, () -> userDao.create(user));
    }

    @DisplayName("Найти всех пользователей")
    @Test
    @Order(7)
    void shouldFindAllUsers() {
        User user1 = User.builder()
                .name("User1")
                .email("user1@email.com")
                .age(20)
                .createdAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .name("User2")
                .email("user2@email.com")
                .age(25)
                .createdAt(LocalDateTime.now())
                .build();

        userDao.create(user1);
        userDao.create(user2);

        List<User> allUsers = userDao.findAll();

        assertTrue(allUsers.size() >= 2);
    }

    @AfterAll
    static void stopContainer() {
        postgres.stop();
    }
}