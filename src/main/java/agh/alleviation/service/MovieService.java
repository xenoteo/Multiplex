package agh.alleviation.service;

import agh.alleviation.model.Movie;
import agh.alleviation.persistence.MovieRepository;
import io.reactivex.rxjava3.core.Observable;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MovieService {
    MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(String name) {
        Movie movie = new Movie(name);
        movieRepository.save(movie);
    }

//    public Observable<Movie> findMovie(String name) {
//        return movieRepository.findByName(name);
//    }
//
//    public List<Movie> getAllMovies() {
//        return new ArrayList<>();
//    }

    public Movie findMovie(String name) {
        return movieRepository.findByName(name);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
