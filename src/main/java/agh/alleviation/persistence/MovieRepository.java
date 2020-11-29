package agh.alleviation.persistence;

import agh.alleviation.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    Movie findByName(String name);

    List<Movie> findAll();
}