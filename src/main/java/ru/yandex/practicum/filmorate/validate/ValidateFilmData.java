package ru.yandex.practicum.filmorate.validate;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class ValidateFilmData {

    private Film film;
    private static final LocalDate dateFirstFilm = LocalDate.of(1895, 12, 28);

    private static final int maxLengthDescription = 200;

    public ValidateFilmData(Film film) {
        this.film = film;
    }

    public boolean checkAllData() {
        if(isCorrectName() && isCorrectLengthDescription() && isCorrectReleaseDate() && isPositiveDuration()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isCorrectName() {
        if(!film.getName().isBlank()) {
            return true;
        } else {
            log.warn("Ошибка во входных данных фильма. Пустое название фильма");
            return false;
        }
    }

    private boolean isCorrectLengthDescription() {
        if(film.getDescription().length() <= maxLengthDescription) {
            return true;
        } else {
            log.warn("Ошибка во входных данных фильма. Превышено максимально допустимое описание фильма в "
                    + maxLengthDescription + " символов. Текущая длина описания фильма "
                    + film.getDescription().length() + " символов.");
            return false;
        }
    }

    private boolean isCorrectReleaseDate() {
        if(film.getReleaseDate().isAfter(dateFirstFilm)) {
            return true;
        } else {
            log.warn("Ошибка во входных данных фильма. Некорректная дата релиза " + film.getReleaseDate()
                    + ". Релиз должен быть позже " + dateFirstFilm);
            return false;
        }
    }

    private boolean isPositiveDuration() {
        if(film.getDuration() > 0) {
            return true;
        } else {
            log.warn("Ошибка во входных данных фильма. Продолжительность фильма должна быть положительной.");
            return false;
        }
    }
}
