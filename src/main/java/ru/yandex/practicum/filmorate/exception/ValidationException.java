package ru.yandex.practicum.filmorate.Exception;

public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
        super(message);
    }
}
