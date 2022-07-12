package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }
    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
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
}
