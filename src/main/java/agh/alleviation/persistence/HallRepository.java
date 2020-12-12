package agh.alleviation.persistence;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository of cinema halls.
 *
 * @author Ksenia Fiodarava
 * @see Hall
 */
public interface HallRepository extends CrudRepository<Hall, Integer> {
    /**
     * Find by capacity greater than equal list.
     *
     * @param capacity the capacity
     * @return the list
     */
    List<Hall> findByCapacityGreaterThanEqual(int capacity);

    /**
     * Find all by capacity list.
     *
     * @param capacity the capacity
     * @return the list
     */
    List<Hall> findAllByCapacity(int capacity);

    @Query("SELECT h FROM Hall h LEFT JOIN FETCH h.seances WHERE h.id = ?1")
    Hall findByIdWithSeances(int id);

    Hall findByNumber(int number);
}
