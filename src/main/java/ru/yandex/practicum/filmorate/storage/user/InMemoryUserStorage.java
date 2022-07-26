package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private static int id = 0;

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }
    @Override
    public User addUser(User user) {
        user.setId(getId());
        users.put(user.getId(), user);
        return user;
    }
    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }
    @Override
    public User getUserById(int id) {
        return users.get(id);
    }
    @Override
    public boolean isContainsUser(int idUser) {
        if(users.containsKey(idUser)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addFriend(int id, int friendId) {
        User firstUser = users.get(id);
        User secondUser = users.get(friendId);
        firstUser.getFriends().add(secondUser.getId());
        secondUser.getFriends().add(firstUser.getId());
        updateUser(firstUser);
        updateUser(secondUser);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        User firstUser = users.get(id);
        User secondUser = users.get(friendId);
        firstUser.getFriends().remove(secondUser.getId());
        secondUser.getFriends().remove(firstUser.getId());
        updateUser(firstUser);
        updateUser(secondUser);
    }

    @Override
    public List<User> mutualFriends(int id, int friendId) {
        User firstUser = users.get(id);
        User secondUser = users.get(friendId);
        List<User> mutualFriends = new ArrayList<>();
        firstUser.getFriends().stream()
                .filter(idUser -> secondUser.getFriends().contains(idUser))
                .forEach(idUser -> mutualFriends.add(users.get(idUser)));
        return mutualFriends;
    }
    @Override
    public List<User> getAllFriend(int id) {
        List<User> mutualFriends = new ArrayList<>();
        User user = users.get(id);
        for(Integer idFriend : user.getFriends()) {
            User friend = users.get(idFriend);
            mutualFriends.add(friend);
        }
        return mutualFriends;
    }

    public int getId() {
        this.id++;
        return id;
    }
}
