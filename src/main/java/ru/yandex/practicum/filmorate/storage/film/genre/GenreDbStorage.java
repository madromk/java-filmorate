package ru.yandex.practicum.filmorate.storage.film.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("genreDbStorage")
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Genre getGenreById(int id) {
        String sql = "SELECT name FROM genres WHERE category_id = ?";
        String genreStr = jdbcTemplate.queryForObject(sql, String.class, id);
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreStr);
        return genre;
    }

    @Override
    public List<Genre> findAllGenres() {
        String sql = "SELECT * FROM genres ORDER BY category_id";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    public Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("genres.category_id"));
        genre.setName(resultSet.getString("genres.name"));
        return genre;
    }
}
