package com.amigoscode.actor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorRepository implements ActorDao {

    private final JdbcTemplate jdbcTemplate;

    public ActorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Actor> selectActorsByMovie(int movieId) {
        String sql = """
                SELECT id, name FROM actor
                WHERE movie = ?;
                """;
        return jdbcTemplate.query(sql, new ActorRowMapper(), movieId);
    }
}
