package com.amigoscode.movie;

import com.amigoscode.actor.Actor;
import com.amigoscode.actor.ActorDao;
import com.amigoscode.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieDao movieDao;
    private final ActorDao actorDao;

    public MovieService(MovieDao movieDao, ActorDao actorDao) {
        this.movieDao = movieDao;
        this.actorDao = actorDao;
    }

    public List<Movie> getMovies() {
        return movieDao.selectMovies()
                .stream()
                .map(m -> {
                    List<Actor> actors = actorDao.selectActorsByMovie(m.id());
                    return new Movie(m.id(), m.name(), actors, m.releaseDate());
                }).toList();
    }

    public void addNewMovie(Movie movie) {
        // TODO: check if movie exists
        int result = movieDao.insertMovie(movie);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteMovie(Integer id) {
        Optional<Movie> movies = movieDao.selectMovieById(id);
        movies.ifPresentOrElse(movie -> {
            int result = movieDao.deleteMovie(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete movie");
            }
        }, () -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });
    }

    public Movie getMovie(int id) {
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }

    public void updateMovie(Movie movie) {
        int result = movieDao.updateMovie(movie);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }
}
