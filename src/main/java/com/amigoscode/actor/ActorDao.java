package com.amigoscode.actor;

import java.util.List;

public interface ActorDao {
    List<Actor> selectActorsByMovie(int movieId);
}
