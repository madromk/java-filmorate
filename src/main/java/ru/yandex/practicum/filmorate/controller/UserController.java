package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();
    private static int id = 0;

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    @ResponseBody
    public User createUser(@RequestBody User user) throws ValidationException {
        if(user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if(checkUser(user)) {
            user.setId(getId());
            users.put(user.getId(), user);
            log.info("Получен запрос к эндпоинту: POST /users" + user);
            return user;
        } else {
            log.warn("Запрос к эндпоинту POST /users не обработан. " +
                    "Введеные данные о пользователе не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько условий не выполняются");
        }
    }
    @PutMapping(value = "/users")
    @ResponseBody
    public User updateUser(@RequestBody User user) throws ValidationException {
        if(user.getName().isEmpty()) {
            user.setName(user.getEmail());
        }
        if(checkUser(user) && user.getId() > 0) {
            log.info("Получен запрос к эндпоинту: PUT /users");
            users.put(user.getId(), user);
            return user;
        } else {
            log.warn("Запрос к эндпоинту PUT /users не обработан. " +
                    "Введеные данные о пользователе не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько условий не выполняются");
        }
    }
    public boolean checkUser(User user) {
        boolean isEmail = !user.getEmail().isEmpty() && user.getEmail().contains("@");
        boolean isLogin = !user.getLogin().isEmpty() && !user.getLogin().contains(" ");
        boolean isBirthday = user.getBirthday().isBefore(LocalDate.now());
        if(isEmail && isLogin && isBirthday) {
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        this.id++;
        return id;
    }
}
