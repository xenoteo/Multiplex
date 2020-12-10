package agh.alleviation.persistence;

import agh.alleviation.model.Genre;
import javafx.beans.property.StringProperty;
import org.springframework.data.repository.CrudRepository;


/**
 * A repository for the movie genres.
 *
 * @author Anna Nosek
 * @see Genre
 */
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    Genre findByName(String name);
}
