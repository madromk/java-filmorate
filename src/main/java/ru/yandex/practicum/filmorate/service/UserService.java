package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;

//    public UserService(InMemoryUserStorage inMemoryUserStorage) {
//        this.userStorage = inMemoryUserStorage;
//    }

    public void addFriend(int id, int friendId) {
        userStorage.addFriend(id, friendId);
    }
    public void deleteFriend(int id, int friendId) {
        userStorage.deleteFriend(id, friendId);
    }
    public List<User> mutualFriends(int id, int friendId) {
        return userStorage.mutualFriends(id, friendId);
    }
    public List<User> getAllFriend(int id) {
        return userStorage.getAllFriend(id);
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }
    public User addUser(User user) {
        return userStorage.addUser(user);
    }
    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }
    public boolean isContainsUser(int idUser) {
        return userStorage.isContainsUser(idUser);
    }

}
