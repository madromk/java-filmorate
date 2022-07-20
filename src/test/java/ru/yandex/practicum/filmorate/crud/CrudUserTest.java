package ru.yandex.practicum.filmorate.crud;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CrudUserTest {
    private final UserDbStorage userDbStorage;

    static User user1 = User.builder()
            .name("name")
            .email("email@yandex.ru")
            .login("login")
            .birthday(LocalDate.of(1994, 10, 01))
            .build();
    static User user2 = User.builder()
            .name("name2")
            .email("email@yandex222.ru")
            .login("login222")
            .birthday(LocalDate.of(1994, 10, 01))
            .build();


    public void addUsers() {
        userDbStorage.addUser(user1);
        userDbStorage.addUser(user2);
        user1.setId(1);
        user2.setId(2);
    }

    @Test
    public void getUserById() {
        addUsers();
        int id1 = 1;
        int id2 = 2;
        User userGet1 = userDbStorage.getUserById(id1);
        User userGet2 = userDbStorage.getUserById(id2);
        assertEquals(user1, userGet1);
        assertEquals(user2, userGet2);
    }
}
