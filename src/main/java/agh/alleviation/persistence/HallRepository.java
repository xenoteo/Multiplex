package agh.alleviation.persistence;

import agh.alleviation.model.Hall;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HallRepository extends CrudRepository<Hall, Integer> {
    List<Hall> findByCapacityGreaterThanEqual(int capacity);
    List<Hall> findAllByCapacity(int capacity);
}
