package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mpa {
    private int id;
    private String name;

    public Mpa() {
    }
//    public Mpa(int id) {
//        this.id = id;
//        this.name = FilmRating.getNameString(id);
//    }
}
