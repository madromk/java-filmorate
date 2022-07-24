package ru.yandex.practicum.filmorate.dbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.ratingMpa.RatingMpaDbStorage;
import ru.yandex.practicum.filmorate.storage.film.ratingMpa.RatingMpaStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RatingMpaDbStorageTest {
    private final RatingMpaDbStorage ratingMpaDbStorage;

    @Test
    public void getGenreById() {
        Mpa mpa = new Mpa();
        mpa.setId(1);
        mpa.setName("G");
        assertEquals(mpa, ratingMpaDbStorage.getMpaRatingById(1));
    }

    @Test
    public void getAllGenres() {
        System.out.println(ratingMpaDbStorage.findAllMpaRatings());
    }
}
