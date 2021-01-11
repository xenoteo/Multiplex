package agh.alleviation.persistence;

import agh.alleviation.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Movie repository.
 *
 * @author Kamil Krzempek
 * @see Movie
 */
public interface MovieRepository extends CrudRepository<Movie, Integer> {
    /**
     * Finds the movie by name .
     *
     * @param name the name
     * @return the movie with given name
     */
    Movie findByName(String name);

    /**
     * Finds the list of all the movies.
     *
     * @return the list of all the movies
     */
    List<Movie> findAll();

    /**
     * Find the movie by id with seances.
     *
     * @param id the id
     * @return the movie
     */
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.seances WHERE m.id = ?1")
    Movie findByIdWithSeances(int id);
}