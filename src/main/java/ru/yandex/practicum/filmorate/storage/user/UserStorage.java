package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> findAllUsers();
    void addUser(User user);
    void updateUser(User user);
    User getUserById(String id);
    boolean isContainsUser(String id);
}
