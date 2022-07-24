package ru.yandex.practicum.filmorate.storage.film.genre;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;


@Component("inMemoryGenreStorage")
public class InMemoryGenreStorage implements GenreStorage {
    public Genre getGenreById(int id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(FilmGenre.getNameString(id));
        return genre;
    }
    public List<Genre> findAllGenres() {
        List<Genre> genres = new ArrayList<>();
        for(String name : FilmGenre.getGenresString()) {
            Genre genre = new Genre();
            genre.setName(name);
            genres.add(genre);
        }
        return genres;
    }
}
