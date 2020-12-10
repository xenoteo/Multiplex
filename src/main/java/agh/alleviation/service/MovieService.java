package agh.alleviation.service;

import agh.alleviation.model.Genre;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import agh.alleviation.persistence.GenreRepository;
import agh.alleviation.persistence.MovieRepository;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The type Movie service.
 *
 * @author Anna Nosek
 */
@Service
@Transactional
public class MovieService extends EntityObjectService<Movie, MovieRepository>{
    private final GenreRepository genreRepository;

    /**
     * Instantiates a new Movie service.
     *
     * @param movieRepository the movie repository
     * @param genreRepository the genre repository
     */
    @Autowired
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.repository = movieRepository;
        this.genreRepository = genreRepository;
    }

    /**
     * Add movie movie.
     *
     * @param name  the name
     * @param genreName the genre
     * @return the movie
     */
    public Movie addMovie(String name, String genreName) {
        Genre genre = genreRepository.findByName(genreName);
        if(genre == null) genre = addGenre(genreName);
        Movie movie = new Movie(name, genre);
        repository.save(movie);
        return movie;
    }

    public void updateMovie(Movie movie) {
        repository.save(movie);
    }

    /**
     * Add genre genre.
     *
     * @param name the name
     * @return the genre
     */
    public Genre addGenre(String name){
        Genre genre = new Genre(name);
        genreRepository.save(genre);
        return genre;
    }


    /**
     * Find movie movie.
     *
     * @param name the name
     * @return the movie
     */
    public Movie findMovie(String name) {
        return repository.findByName(name);
    }

    /**
     * Gets all movies.
     *
     * @return the all movies
     */
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }


    public List<Movie> getActiveMovies(){ return repository.findAll().stream().filter(Movie::getIsActive).collect(Collectors.toList()); }

    public void delete(Movie movie){
        movie = repository.findByIdWithSeances(movie.getId());
        super.delete(movie);
    }
}
