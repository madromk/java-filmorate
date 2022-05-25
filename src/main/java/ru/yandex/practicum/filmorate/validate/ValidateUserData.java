package ru.yandex.practicum.filmorate.validate;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class ValidateUserData {

    private final User user;

    public ValidateUserData(User user) {
        this.user = user;
    }

    public boolean checkEmail() {
        if(!user.getEmail().isEmpty() && user.getEmail().contains("@")) {
            return true;
        } else {
            log.warn("Ошибка во входных данных. Электронная почта пустая или не содержит @");
            return false;
        }
    }

    public boolean checkLogin() {
        if(!user.getLogin().isEmpty() && !user.getLogin().contains(" ")) {
            return true;
        } else {
            log.warn("Ошибка во входных данных. Логин пустой или содержит пробелы");
            return false;
        }
    }

    public boolean checkBirthday() {
        if(user.getBirthday().isBefore(LocalDate.now())) {
            return true;
        } else {
            log.warn("Ошибка во входных данных. Дата рождения указана в будующем");
            return false;
        }
    }

    public boolean checkAllData() {
        if(checkEmail() && checkLogin() && checkBirthday()) {
            return true;
        } else {
            return false;
        }
    }
}
