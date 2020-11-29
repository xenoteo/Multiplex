package agh.alleviation.persistence;

import agh.alleviation.model.Genre;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Anna Nosek
 * A repository for the movie genres.
 */
public interface GenreRepository extends CrudRepository<Genre, Integer> {

}
