package agh.alleviation.service;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Hall;
import agh.alleviation.model.Seance;
import agh.alleviation.persistence.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service responsible for manipulating the hall repository.
 *
 * @author Ksenia Fiodarava
 * @see HallRepository
 * @see Hall
 */
@Service
@Transactional
public class HallService extends EntityObjectService<Hall, HallRepository> {
//    private final HallRepository hallRepository;

    /**
     * Instantiates a new Hall service.
     *
     * @param hallRepository the hall repository
     */
    @Autowired
    public HallService(HallRepository hallRepository) {
        this.repository = hallRepository;
    }

    /**
     * Add hall
     *
     * @param capacity the capacity
     * @param number   the number
     * @return the hall
     */
    public Hall addHall(int capacity, int number){
        Hall hall = new Hall(capacity, number);
        repository.save(hall);
        return hall;
    }

    public void updateHall(Hall hall) {
        repository.save(hall);
    }

    /**
     * Get all halls list.
     *
     * @return the list
     */
    public List<Hall> getAllHalls(){
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(EntityObject hall){
        hall = repository.findByIdWithSeances(hall.getId());
        super.delete(hall);
    }

    /**
     * Find halls by capacity greater than list.
     *
     * @param capacity the capacity
     * @return the list
     */
    public List<Hall> findHallsByCapacityGreaterThan(int capacity){
        return repository.findByCapacityGreaterThanEqual(capacity);
    }

    /**
     * Find halls by capacity list.
     *
     * @param capacity the capacity
     * @return the list
     */
    public List<Hall> findHallsByCapacity(int capacity){
        return repository.findAllByCapacity(capacity);
    }
}
