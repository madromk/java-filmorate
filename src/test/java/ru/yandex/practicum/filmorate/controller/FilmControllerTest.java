package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController = new FilmController();
    static Film film = Film.builder()
            .id(1)
            .name("name1")
            .description("description1")
            .duration(120)
            .releaseDate(LocalDate.of(2021, 5, 21))
            .build();

    @AfterEach
    public void film() {
        film = Film.builder()
                .id(1)
                .name("name1")
                .description("description1")
                .duration(120)
                .releaseDate(LocalDate.of(2021, 5, 21))
                .build();
    }

    @Test
    public void checkMethodPost() throws ValidationException {
        filmController.createFilm(film);
    }

    @Test
    public void checkNameThrow() throws ValidationException {
        //Зададим пустое имя
        film.setName("");
        assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() {
                filmController.createFilm(film);
            }
        });
    }

    @Test
    public void checkDateThrow() {
        //Зададим неправильную дату
        film.setReleaseDate(LocalDate.of(1800, 2,2));
        assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() {
                filmController.createFilm(film);
            }
        });
    }

    @Test
    public void checkLengthDescriptionThrow() {
        String repeated = " ".repeat(201);
        film.setDescription(repeated);
        assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() {
                filmController.createFilm(film);
            }
        });
    }
    @Test
    public void checkDescriptionThrow() {
        film.setDuration(-1);
        assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() {
                filmController.createFilm(film);
            }
        });
    }

}