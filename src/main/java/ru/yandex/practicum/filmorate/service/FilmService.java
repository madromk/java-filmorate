package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InputDataException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    @Autowired
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;

//    public FilmService(FilmStorage filmStorage) {
//        this.filmStorage = filmStorage;
//    }

    public void addLike(int filmId, int userId) {
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(String count) {
        return filmStorage.getPopularFilms(count);
    }

    public Film getFilmById(int id) {
        if(!isContainsFilms(id)) {
            log.warn("Запрос к эндпоинту GET не обработан. Фильм с таким id не найден. id = " + id);
            throw new InputDataException("Фильм с таким id не найден");
        }
        return filmStorage.getFilmById(id);
    }
    public Genre getGenreById(int id) {
        if(id < 1) {
            log.warn("Запрос к эндпоинту GET не обработан. Жанр с таким id не найден. id = " + id);
            throw new InputDataException("Некорректный id жанра");
        }
        return filmStorage.getGenreById(id);
    }

    public Mpa getRatingById(int id) {
        System.out.println(2);
        if(id < 1) {
            log.warn("Запрос к эндпоинту GET не обработан. Рейтинг с таким id не найден. id = " + id);
            throw new InputDataException("Некорректный id рейтинга");
        }
        return filmStorage.getMpaRatingById(id);
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public List<Genre> findAllGenres() {
        return filmStorage.findAllGenres();
    }

    public List<Mpa> findAllRatings() {
        return filmStorage.findAllMpaRatings();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
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
