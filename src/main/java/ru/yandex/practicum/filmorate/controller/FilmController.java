package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InputDataException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validate.ValidateFilmData;

import java.util.HashSet;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private static final String POPULAR_FILMS = "10";
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Получен запрос к эндпоинту: GET /films");
        return filmService.findAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmByID(@PathVariable("id") int id) {
        log.info("Получен запрос к эндпоинту: GET /films/{id}");
        return filmService.getFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) String count) {
        log.info("Получен запрос к эндпоинту: GET /films/popular");
        if(count != null) {
            return filmService.getPopularFilms(count);
        } else {
            return filmService.getPopularFilms(POPULAR_FILMS);
        }
    }

    @PostMapping("/films")
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        if(film.getAmountLikes() == null) {
            film.setAmountLikes(new HashSet<>());
        }
        if(new ValidateFilmData(film).checkAllData()) {
            log.info("Получен запрос к эндпоинту: POST /films");
            return new ResponseEntity<>(filmService.addFilm(film), HttpStatus.CREATED);
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    @PutMapping("/films")
    public ResponseEntity<Film> updateFilm(@RequestBody Film film) {
        if(film.getAmountLikes() == null) {
            film.setAmountLikes(new HashSet<>());
        }
        if(!filmService.isContainsFilms(film.getId())) {
            throw new InputDataException("Фильм c таким id не найден");
        }
        if(new ValidateFilmData(film).checkAllData() && film.getId() > 0) {
            log.info("Получен запрос к эндпоинту: PUT /films обновление фильма");
            filmService.updateFilm(film);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        log.info("Получен запрос к эндпоинту: PUT /films добавление лайка к фильму " + id + ", пользователя " + userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        log.info("Получен запрос к эндпоинту: DELETE /films удаление лайка к фильму " + id + ", " +
                "пользователя " + userId);
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

}
