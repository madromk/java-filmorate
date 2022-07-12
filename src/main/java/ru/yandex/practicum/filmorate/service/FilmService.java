package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage fileStorage) {
        this.filmStorage = fileStorage;
    }

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        film.getAmountLikes().add(userId);
        filmStorage.updateFilm(film);
    }

    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        film.getAmountLikes().remove(userId);
        filmStorage.updateFilm(film);
    }

    public List<Film> getPopularFilms(String count) {
        return filmStorage.findAllFilms().stream()
                .filter(film -> film.getAmountLikes() != null)
                .sorted(sortPopularFilm())
                .limit(Integer.parseInt(count))
                .collect(Collectors.toList());
    }

    public Comparator<Film> sortPopularFilm() {
        return Comparator.comparing(film -> film.getAmountLikes().size(), Comparator.reverseOrder());
    }
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }
    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public void addFilm(Film film) {
        filmStorage.addFilm(film);
    }
    public void updateFilm(Film film) {
        filmStorage.updateFilm(film);
    }
    public boolean isContainsFilms(int id) {
        return filmStorage.isContainsFilms(id);
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }


}
