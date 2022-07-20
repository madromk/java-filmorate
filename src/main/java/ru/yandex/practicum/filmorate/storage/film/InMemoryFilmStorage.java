package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InputDataException;
import ru.yandex.practicum.filmorate.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Component("inMemoryFilmStorage")
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private static int id = 0;

    @Override
    public Film getFilmById(int id) {
        return films.get(id);
    }

    @Override
    public Mpa getMpaRatingById(int id) {
        Mpa mpa = new Mpa();
        mpa.setId(id);
        mpa.setName(FilmRating.getNameString(id));
        return mpa;
    }

    @Override
    public Genre getGenreById(int id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(FilmGenre.getNameString(id));
        return genre;
    }
    @Override
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Mpa> findAllMpaRatings() {
        return new ArrayList<>();
    }

    @Override
    public List<Genre> findAllGenres() {
        return new ArrayList<>();
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(int filmId, int userId) {
        Film film = films.get(filmId);
        film.getAmountLikes().add(userId);
        updateFilm(film);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        if(!isContainsFilms(filmId)) {
            log.warn("Запрос к эндпоинту DELETE не обработан. Фильм с таким id не найден. id = " + filmId);
            throw new InputDataException("Фильм с таким id не найден");
        }
        if(userId < 0) {
            throw new InputDataException("Пользователь с таким id не найден");
        }
        Film film = films.get(filmId);
        film.getAmountLikes().remove(userId);
        updateFilm(film);
    }

    @Override
    public void updateFilm(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public boolean isContainsFilms(int id) {
        return films.containsKey(id);
    }
    @Override
    public List<Film> getPopularFilms(String count) {
        return findAllFilms().stream()
                .filter(film -> film.getAmountLikes() != null)
                .sorted(sortPopularFilm())
                .limit(Integer.parseInt(count))
                .collect(Collectors.toList());
    }

    public Comparator<Film> sortPopularFilm() {
        return Comparator.comparing(film -> film.getAmountLikes().size(), Comparator.reverseOrder());
    }

    public int getId() {
        this.id++;
        return id;
    }

}
