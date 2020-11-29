package agh.alleviation.persistence;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Seance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Ksenia Fiodarava
 * Repository of cinema halls.
 * @see Hall
 */
public interface HallRepository extends CrudRepository<Hall, Integer> {
    List<Hall> findByCapacityGreaterThanEqual(int capacity);
    List<Hall> findAllByCapacity(int capacity);
}
