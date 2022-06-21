package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    public FilmService() {
        this.filmStorage = new InMemoryFilmStorage();
    }

    public void addLike(String filmId, String userId) {
        Film film = filmStorage.getFilmById(filmId);
        film.addLike(Integer.parseInt(userId));
        filmStorage.updateFilm(film);
    }

    public void removeLike(String filmId, String userId) {
        Film film = filmStorage.getFilmById(filmId);
        film.removeLike(Integer.parseInt(userId));
        filmStorage.updateFilm(film);
    }

    public List<Film> getPopularFilms(String count) {
        int amount = Integer.parseInt(count);
        List<Film> result = filmStorage.findAllFilms().stream()
                .filter(film -> film.getAmountLikes() != null)
                .sorted(Comparator.comparing(film -> film.getAmountLikes().size(), Comparator.reverseOrder()))
                .limit(amount)
                .collect(Collectors.toList());
        return result;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }
}
