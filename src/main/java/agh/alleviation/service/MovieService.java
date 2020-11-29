package agh.alleviation.service;

import agh.alleviation.model.Genre;
import agh.alleviation.model.Movie;
import agh.alleviation.persistence.GenreRepository;
import agh.alleviation.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    public Movie addMovie(String name, Genre genre) {
        Movie movie = new Movie(name, genre);
        movieRepository.save(movie);
        return movie;
    }

    public Genre addGenre(String name){
        Genre genre = new Genre(name);
        genreRepository.save(genre);
        return genre;
    }


    public Movie findMovie(String name) {
        return movieRepository.findByName(name);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
