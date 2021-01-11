package agh.alleviation.persistence;

import agh.alleviation.model.Hall;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository of cinema halls.
 *
 * @see Hall
 * @author Ksenia Fiodarava
 */
public interface HallRepository extends CrudRepository<Hall, Integer> {
    /**
     * Finds the halls by capacity greater than equal.
     *
     * @param capacity  the capacity
     * @return the list of the halls with given capacity
     */
    List<Hall> findByCapacityGreaterThanEqual(int capacity);

    /**
     * Finds all halls by capacity.
     *
     * @param capacity  the capacity
     * @return the list of the halls with given capacity
     */
    List<Hall> findAllByCapacity(int capacity);

    /**
     * Finds the hall by id with seances.
     *
     * @param id  the id
     * @return the hall
     */
    @Query("SELECT h FROM Hall h LEFT JOIN FETCH h.seances WHERE h.id = ?1")
    Hall findByIdWithSeances(int id);

    /**
     * Finds the hall by number.
     *
     * @param number  the number
     * @return the hall with given number
     */
    Hall findByNumber(int number);
}
