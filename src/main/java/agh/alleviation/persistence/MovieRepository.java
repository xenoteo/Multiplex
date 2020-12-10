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
     * Find by name movie.
     *
     * @param name the name
     * @return the movie
     */
    Movie findByName(String name);

    List<Movie> findAll();


    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.seances WHERE m.id = ?1")
    List<Movie> findByIdWithSeances(int id);

}