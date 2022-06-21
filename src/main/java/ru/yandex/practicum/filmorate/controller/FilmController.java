package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InputDataException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validate.ValidateFilmData;

import java.util.HashSet;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private static final String TEN_POPULAR_FILMS = "10";
    private final FilmService filmService;
    private final FilmStorage filmStorage;
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
        this.filmStorage = filmService.getFilmStorage();
    }

    private static int id = 0;

    @GetMapping("/films")
    @ResponseBody
    public List<Film> findAllFilms() {
        log.info("Получен запрос к эндпоинту: GET /films");
        return filmStorage.findAllFilms();
    }

    @GetMapping("/films/{id}")
    @ResponseBody
    public Film getFilmByID(@PathVariable String id) {
        log.info("Получен запрос к эндпоинту: GET /films/{id}");
        if(!filmStorage.isContainsFilms(id)) {
            throw new InputDataException("Фильм с таким id не найден");
        }
        return filmStorage.getFilmById(id);
    }

    @GetMapping("/films/popular")
    @ResponseBody
    public List<Film> getPopularFilms(@RequestParam(required = false) String count) {
        log.info("Получен запрос к эндпоинту: GET /films/popular");
        if(count != null) {
            return filmService.getPopularFilms(count);
        } else {
            return filmService.getPopularFilms(TEN_POPULAR_FILMS);
        }
    }

    @PostMapping("/films")
    @ResponseBody
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        if(film.getAmountLikes() == null) {
            film.setAmountLikes(new HashSet<>());
        }
        if(new ValidateFilmData(film).checkAllData()) {
            log.info("Получен запрос к эндпоинту: POST /films");
            film.setId(getId());
            filmStorage.addFilm(film);
            return new ResponseEntity<>(film, HttpStatus.CREATED);
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    @PutMapping("/films")
    @ResponseBody
    public ResponseEntity<Film> updateFilm(@RequestBody Film film) {
        if(film.getAmountLikes() == null) {
            film.setAmountLikes(new HashSet<>());
        }
        if(!filmStorage.isContainsFilms(String.valueOf(film.getId()))) {
            throw new InputDataException("Фильм c таким id не найден");
        }
        if(new ValidateFilmData(film).checkAllData() && film.getId() > 0) {
            log.info("Получен запрос к эндпоинту: PUT /films обновление фильма");
            filmStorage.updateFilm(film);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен запрос к эндпоинту: PUT /films добавление лайка к фильму " + id + ", пользователя " + userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен запрос к эндпоинту: DELETE /films добавление лайка к фильму " + id + ", " +
                "пользователя " + userId);
        if(!filmStorage.isContainsFilms(id)) {
            log.warn("Запрос к эндпоинту DELETE не обработан. Фильм с таким id не найден. id = " + id);
            throw new InputDataException("Фильм с таким id не найден");
        }
        if(Integer.parseInt(userId) < 0) {
            throw new InputDataException("Пользователь с таким id не найден");
        }
        filmService.removeLike(id, userId);
    }
    @ExceptionHandler
    public ResponseEntity<String> handleIncorrectValidation(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        log.warn("При обработке запроса возникло исключение " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<String> handleNotFoundException(InputDataException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    public int getId() {
        this.id++;
        return id;
    }
}
