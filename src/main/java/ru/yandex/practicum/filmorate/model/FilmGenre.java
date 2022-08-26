package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public enum FilmGenre {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    ANIMATION("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION("Боевик");

    private String name;
    FilmGenre(String name) {
        this.name = name;
    }
    public String getNameGenre() {
        return name;
    }

    public static FilmGenre checkGenre(String str) {
        for (FilmGenre genre : FilmGenre.values()) {
            if(str.equals(genre.getNameGenre())) {
                return genre;
            }
        }
        throw new ValidationException("Имя жанра нет в базе данных");
    }

    public static String getNameString(int id) {
        List<FilmGenre> genresName = List.of(FilmGenre.values());
        return genresName.get(id -1).getNameGenre();
    }

    public static List<String> getGenresString() {
        List<String> str = new ArrayList<>();
        for(FilmGenre genre : FilmGenre.values()) {
            str.add(genre.getNameGenre());
        }
        return str;
    }
}
