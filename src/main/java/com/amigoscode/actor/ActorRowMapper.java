package com.amigoscode.actor;

import com.amigoscode.movie.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ActorRowMapper implements RowMapper<Actor> {
    @Override
    public Actor mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Actor(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }
}
