package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController = new UserController();

    static User user = User.builder()
            .name("name")
            .email("email@yandex.ru")
            .login("login")
            .birthday(LocalDate.of(1994, 10, 01))
            .build();

    static User user2 = User.builder()
            .name("name2")
            .email("email2@yandex.ru")
            .login("login2")
            .birthday(LocalDate.of(1991, 10, 01))
            .build();

    @AfterEach
    public void user() {
        user = User.builder()
                .name("name")
                .email("email@yandex.ru")
                .login("login")
                .birthday(LocalDate.of(1994, 10, 01))
                .build();
    }

    @Test
    public void checkMethodPost() {
        userController.createUser(user);
        userController.createUser(user2);
        assertEquals(2, userController.findAllUsers().size());
    }

    @Test
    public void checkMethodPostWithoutName() {
        user.setName("");
        userController.createUser(user);
        System.out.println(userController.findAllUsers());
    }

    @Test
    public void checkEmailThrow() {
        user.setEmail("abc");
        assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() {
                userController.createUser(user);
            }
        });
    }

    @Test
    public void checkLoginThrow() {
        user.setLogin("Login 1");
        assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() {
                userController.createUser(user);
            }
        });
    }

    @Test
    public void checkBirthday() {
        user.setBirthday(LocalDate.of(2030, 10, 01));
        assertTrue(LocalDate.now().isBefore(user.getBirthday()));
    }

}