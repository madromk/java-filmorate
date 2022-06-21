package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Data
@Builder
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    @Builder.Default
    private Set<Integer> amountLikes = new HashSet<>();

    public void addLike(int idUser) {
        amountLikes.add(idUser);
    }
    public void removeLike(int idUser) {
        amountLikes.remove(idUser);
    }
}
