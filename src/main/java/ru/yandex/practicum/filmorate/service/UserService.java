package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.userStorage = inMemoryUserStorage;
    }

    public void addFriend(int id, int friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);
        firstUser.getFriends().add(secondUser.getId());
        secondUser.getFriends().add(firstUser.getId());
        userStorage.updateUser(firstUser);
        userStorage.updateUser(secondUser);
    }
    public void deleteFriend(int id, int friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);
        firstUser.getFriends().remove(secondUser.getId());
        secondUser.getFriends().remove(firstUser.getId());
        userStorage.updateUser(firstUser);
        userStorage.updateUser(secondUser);
    }
    public List<User> mutualFriends(int id, int friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);
        List<User> mutualFriends = new ArrayList<>();
        firstUser.getFriends().stream()
                .filter(idUser -> secondUser.getFriends().contains(idUser))
                .forEach(idUser -> mutualFriends.add(userStorage.getUserById(idUser)));
        return mutualFriends;
    }
    public List<User> getAllFriend(int id) {
        List<User> mutualFriends = new ArrayList<>();
        User user = userStorage.getUserById(id);
        for(Integer idFriend : user.getFriends()) {
            User friend = userStorage.getUserById(idFriend);
            mutualFriends.add(friend);
        }
        return mutualFriends;
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }
    public void addUser(User user) {
        userStorage.addUser(user);
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
