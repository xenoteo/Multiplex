package agh.alleviation.persistence;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Seance;
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
}
