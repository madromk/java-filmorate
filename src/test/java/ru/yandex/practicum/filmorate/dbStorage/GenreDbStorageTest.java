package ru.yandex.practicum.filmorate.dbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.genre.GenreDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {

    private final GenreDbStorage genreDbStorage;

    @Test
    public void getGenreById() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Комедия");
        assertEquals(genre, genreDbStorage.getGenreById(1));
    }

    @Test
    public void getAllGenres() {
        System.out.println(genreDbStorage.findAllGenres());
    }
}
