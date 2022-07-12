package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {
    Film getFilmById(int id);
    List<Film> findAllFilms();
    void addFilm(Film film);
    void updateFilm(Film film);

    boolean isContainsFilms(int id);

}
