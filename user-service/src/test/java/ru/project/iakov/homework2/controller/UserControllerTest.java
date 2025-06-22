package ru.project.iakov.homework2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.project.iakov.homework2.dto.UserDto;
import ru.project.iakov.homework2.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Пользователь найден по ID")
    @Test
    void getUserById_whenUserExist() throws Exception {
        UserDto userDto = new UserDto(1L, "Ivan", "ivan@mail.ru", 30, LocalDateTime.now());
        given(userService.findById(1L)).willReturn(userDto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"));
    }

    @DisplayName("Такого пользователя нет")
    @Test
    void getUserById_whenUserNotExist() throws Exception {
        given(userService.findById(999L)).willThrow(new IllegalArgumentException());

        mockMvc.perform(get("/users/999"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
    }

    @DisplayName("Список пользователей")
    @Test
    void getAllUsers() throws Exception {
        List<UserDto> users = List.of(
                new UserDto(1L, "Ivan", "ivan@mail.ru", 30, LocalDateTime.now()),
                new UserDto(2L, "Petr", "petr@mail.ru", 28, LocalDateTime.now())
        );
        given(userService.findAll()).willReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @DisplayName("Создание пользователя")
    @Test
    void createUser() throws Exception {
        UserDto toCreate = new UserDto(null, "Ivan", "ivan@mail.ru", 30, LocalDateTime.now());
        UserDto created = new UserDto(10L, "Petr", "petr@mail.ru", 28, LocalDateTime.now());

        given(userService.createUser(any(UserDto.class))).willReturn(created);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @DisplayName("Обновление пользователя")
    @Test
    void updateUser_whenUserExist() throws Exception {
        UserDto update = new UserDto(null, "Ivan", "ivan@mail.ru", 30, LocalDateTime.now());
        UserDto updated = new UserDto(1L, "Petr", "petr@mail.ru", 28, LocalDateTime.now());

        given(userService.updateUser(eq(1L), any(UserDto.class))).willReturn(updated);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Petr"));
    }

    @DisplayName("При обновлении пользователь не найден")
    @Test
    void updateUser_whenUserIsNotExist() throws Exception {
        given(userService.updateUser(eq(999L), any(UserDto.class)))
                .willThrow(new IllegalArgumentException());

        mockMvc.perform(put("/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserDto(1L, "Ivan", "ivan@mail.ru", 30, LocalDateTime.now()))))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
    }

    @DisplayName("Успешное удаление пользователя")
    @Test
    void deleteUserSuccess_whenUserExist() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("При удалении пользователь не найден")
    @Test
    void deleteUser_whenUserIsNotExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(userService).deleteUser(999L);

        mockMvc.perform(delete("/users/999"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
    }
}