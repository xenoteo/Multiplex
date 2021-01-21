package agh.alleviation.service;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Hall;
import agh.alleviation.persistence.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service responsible for manipulating the hall repository.
 *
 * @author Ksenia Fiodarava
 * @see EntityObjectService
 * @see HallRepository
 * @see Hall
 */
@Service
@Transactional
public class HallService extends EntityObjectService<Hall, HallRepository> {
    /**
     * Instantiates a new Hall service.
     *
     * @param hallRepository the hall repository
     */
    @Autowired
    public HallService(HallRepository hallRepository) {
        repository = hallRepository;
    }

    /**
     * Adds a hall.
     *
     * @param capacity the capacity
     * @param number   the number
     * @return the hall
     */
    public Hall addHall(int capacity, int number) {
        Hall hall = new Hall(capacity, number);
        repository.save(hall);
        return hall;
    }

    /**
     * Updates a hall.
     *
     * @param hall  the hall
     */
    @Override
    public List<EntityObject> update(EntityObject hall) {
        repository.save((Hall) hall);
        EntityObject newHall = repository.findByIdWithSeances(hall.getId());
        return super.update(newHall);
    }

    /**
     * Overrides method to get seances associated with hall.
     *
     * Because of lazy loading, they are not loaded at the object creation.
     *
     * @param hall  hall to delete
     * @return list of entity objects deleted with hall
     */
    @Override
    public List<EntityObject> delete(EntityObject hall) {
        hall = repository.findByIdWithSeances(hall.getId());
        return super.delete(hall);
    }

    /**
     * Finds halls by capacity greater than given value
     *
     * @param capacity the capacity
     * @return the list of halls
     */
    public List<Hall> findHallsByCapacityGreaterThan(int capacity) {
        return repository.findByCapacityGreaterThanEqual(capacity);
    }

    /**
     * Finds halls by capacity list.
     *
     * @param capacity the capacity
     * @return the list of halls
     */
    public List<Hall> findHallsByCapacity(int capacity) {
        return repository.findAllByCapacity(capacity);
    }

    /**
     * Finds hall by its id.
     *
     * @param number hall's id
     * @return the hall
     */
    public Hall findHallByNumber(int number) {
        return repository.findByNumber(number);
    }

    /**
     * Finds all the halls.
     *
     * @return the list of all the halls
     */
    public Iterable<Hall> findAllHalls(){ return repository.findAll(); }
}
