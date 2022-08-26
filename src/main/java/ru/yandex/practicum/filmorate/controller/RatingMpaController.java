package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.InputDataException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;


import java.util.List;


@RestController
@Slf4j
public class RatingMpaController {

    private final FilmService filmService;
    @Autowired
    public RatingMpaController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/mpa/{id}")
    public Mpa getMpaRatingById(@PathVariable("id") int id) {
        log.info("Получен запрос к эндпоинту GET /mpa/{id}");
        return filmService.getRatingById(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> getMpaRating() {
        log.info("Получен запрос к эндпоинту: GET /mpa");
        return filmService.findAllRatings();
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
