package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private HashMap<Integer,Film> films = new HashMap<>();
    private static final LocalDate dateFirstFilm = LocalDate.of(1895, 12, 28);

    private static int id = 0;

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    @ResponseBody
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        if(checkFilm(film)) {
            log.info("Получен запрос к эндпоинту: POST /films");
            film.setId(getId());
            films.put(film.getId(), film);
            return film;
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if(checkFilm(film) && film.getId() > 0) {
            log.info("Получен запрос к эндпоинту: PUT /films");
            films.put(film.getId(), film);
            return film;
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    public boolean checkFilm(Film film) {
        boolean isName = !film.getName().isBlank();
        boolean isDescription = film.getDescription().length() <= 200;
        boolean isDuration = film.getDuration() > 0;
        boolean isReleaseDate = film.getReleaseDate().isAfter(dateFirstFilm);
        if(isName && isDescription && isDuration && isReleaseDate) {
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        this.id++;
        return id;
    }
}
