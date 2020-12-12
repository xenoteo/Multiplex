package agh.alleviation.service;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Genre;
import agh.alleviation.model.Movie;
import agh.alleviation.persistence.GenreRepository;
import agh.alleviation.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service responsible for manipulating the movie repository.
 *
 * @author Anna Nosek
 */
@Service
@Transactional
public class MovieService extends EntityObjectService<Movie, MovieRepository> {
    private final GenreRepository genreRepository;

    /**
     * Instantiates a new Movie service.
     *
     * @param movieRepository the movie repository
     * @param genreRepository the genre repository
     */
    @Autowired
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {
        repository = movieRepository;
        this.genreRepository = genreRepository;
    }

    /**
     * Add movie
     *
     * @param name        the name
     * @param genreName   the genre
     * @param description the description
     * @param director    the director
     * @param actors      the actors
     * @return the movie
     */
    public Movie addMovie(String name, String genreName, String description, String director, String actors) {
        Genre genre = getGenre(genreName);
        Movie movie = new Movie(name, genre, description, director, actors);
        repository.save(movie);
        return movie;
    }

    /**
     * Get genre of given name, if such genre does not exist, create it first
     *
     * @param name the name
     * @return the genre
     */
    public Genre getGenre(String name) {
        Genre genre = genreRepository.findByName(name);
        if (genre == null) genre = new Genre(name);
        genreRepository.save(genre);
        return genre;
    }

    /**
     * Find movie by name
     *
     * @param name the name
     * @return the movie
     */
    public Movie findMovie(String name) {
        return repository.findByName(name);
    }

    /**
     * Override method to get seances associated with movie
     * Because of lazy loading, they are not loaded at the object creation.
     *
     * @param movie movie to delete
     * @return list of entity objects deleted with movie
     */
    @Override
    public List<EntityObject> delete(EntityObject movie) {
        movie = repository.findByIdWithSeances(movie.getId());
        return super.delete(movie);
    }
}
