package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.*;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {
    Film getFilmById(int id);
//    Genre getGenreById(int id);
//    Mpa getMpaRatingById(int id);
    List<Film> findAllFilms();
//    List<Genre> findAllGenres();
//    List<Mpa> findAllMpaRatings();
    List<Film> getPopularFilms(String count);
    Film addFilm(Film film);
    void updateFilm(Film film);
    boolean isContainsFilms(int id);
    void addLike(int filmId, int userId);
    void removeLike(int filmId, int userId);

}
