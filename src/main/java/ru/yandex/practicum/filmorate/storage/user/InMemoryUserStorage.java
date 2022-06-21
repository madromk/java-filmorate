package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();

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
    public User getUserById(String id) {
        return users.get(Integer.parseInt(id));
    }
    @Override
    public boolean isContainsUser(String idUser) {
        int id = Integer.parseInt(idUser);
        if(users.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }
}
