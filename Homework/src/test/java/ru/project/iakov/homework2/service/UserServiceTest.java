package ru.project.iakov.homework2.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.project.iakov.homework2.User;
import ru.project.iakov.homework2.dao.UserDao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        userService.createUser(user);

        verify(userDao, times(1)).create(user);
    }

    /**
     * Тесты на проверку поиска пользаков
     */
    @DisplayName("Пользователь найден по ID")
    @Test
    public void findById_shouldReturnUser_whenUserExist() {
        User user = new User();
        user.setId(1L);

        when(userDao.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(userDao, times(1)).findById(1L);
    }

    @DisplayName("Такого пользователя нет")
    @Test
    public void findById_shouldReturnEmpty_whenUserNotExist() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(1L);

        assertFalse(result.isPresent());
        verify(userDao, times(1)).findById(1L);
    }

    @DisplayName("Отрицательный ID — выбрасывается исключение")
    @Test
    void findById_shouldThrowException_whenIdIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> userService.findById(-1L));
        verifyNoInteractions(userDao);
    }

    @DisplayName("ID равен нулю — выбрасывается исключение")
    @Test
    void findById_shouldThrowException_whenIdIsZero() {
        assertThrows(IllegalArgumentException.class, () -> userService.findById(0L));
        verifyNoInteractions(userDao);
    }

    @DisplayName("ID равен null — выбрасывается исключение")
    @Test
    void findById_shouldThrowException_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.findById(null));
        verifyNoInteractions(userDao);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userDao.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userDao, times(1)).findAll();
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(2L);

        userService.update(user);

        verify(userDao, times(1)).update(user);
    }

    @Test
    public void testDeleteUser() {
        userService.delete(3L);

        verify(userDao, times(1)).delete(3L);
    }
}