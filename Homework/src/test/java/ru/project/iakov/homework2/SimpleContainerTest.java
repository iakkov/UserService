package ru.project.iakov.homework2;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.project.iakov.homework2.dao.UserDao;
import ru.project.iakov.homework2.dao.UserDaoImpl;

import java.time.LocalDateTime;
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

    @Test
    void testCreateAndFindById() {
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

    @AfterAll
    static void stopContainer() {
        postgres.stop();
    }
}