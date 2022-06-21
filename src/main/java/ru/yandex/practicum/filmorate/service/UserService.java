package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService() {
        this.userStorage = new InMemoryUserStorage();
    }
    public UserStorage getUserStorage() {
        return userStorage;
    }
    public void addFriend(String id, String friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);
        firstUser.getFriends().add(secondUser.getId());
        secondUser.getFriends().add(firstUser.getId());
        userStorage.updateUser(firstUser);
        userStorage.updateUser(secondUser);
    }
    public void deleteFriend(String id, String friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);
        firstUser.getFriends().remove(secondUser.getId());
        secondUser.getFriends().remove(firstUser.getId());
        userStorage.updateUser(firstUser);
        userStorage.updateUser(secondUser);
    }
    public List<User> mutualFriends(String id, String friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);
        List<User> mutualFriends = new ArrayList<>();
        for(Integer idUser : firstUser.getFriends()) {
            if(secondUser.getFriends().contains(idUser)) {
                mutualFriends.add(userStorage.getUserById(String.valueOf(idUser)));
            }
        }
        return mutualFriends;
    }
    public List<User> getAllFriend(String id) {
        List<User> mutualFriends = new ArrayList<>();
        User user = userStorage.getUserById(id);
        for(Integer idFriend : user.getFriends()) {
            User friend = userStorage.getUserById(String.valueOf(idFriend));
            mutualFriends.add(friend);
        }
        return mutualFriends;
    }
}
