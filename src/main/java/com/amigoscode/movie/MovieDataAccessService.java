package com.amigoscode.movie;

import com.amigoscode.actor.Actor;
import com.amigoscode.actor.ActorDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDataAccessService implements MovieDao {

    private final JdbcTemplate jdbcTemplate;
    private final ActorDao actorDao;

    public MovieDataAccessService(JdbcTemplate jdbcTemplate, ActorDao actorDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.actorDao = actorDao;
    }

    @Override
    public List<Movie> selectMovies() {
        var sql = """
                SELECT id, name, release_date
                FROM movie
                LIMIT 100;
                 """;
        return jdbcTemplate.query(sql, new MovieRowMapper()).stream()
                .map(movie -> {
                    List<Actor> actors = actorDao.selectActorsByMovie(movie.id());
                    return new Movie(movie.id(), movie.name(), actors, movie.releaseDate());
                }).toList();
    }

    @Override
    public int insertMovie(Movie movie) {
        var sql = """
                INSERT INTO movie(name, release_date)
                VALUES (?, ?);
                 """;
        return jdbcTemplate.update(
                sql,
                movie.name(), movie.releaseDate()
        );
    }

    @Override
    public int deleteMovie(int id) {
        var sql = """
                DELETE FROM movie   
                WHERE id = ?
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        var sql = """
                SELECT id, name, release_date
                FROM movie
                WHERE id = ?
                 """;
        return jdbcTemplate.query(sql, new MovieRowMapper(), id)
                .stream()
                .findFirst();
    }

}
