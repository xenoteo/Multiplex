package agh.alleviation.service;

import agh.alleviation.model.Hall;
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
public class HallService {
    private final HallRepository hallRepository;

    /**
     * Instantiates a new Hall service.
     *
     * @param hallRepository the hall repository
     */
    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    /**
     * Add hall hall.
     *
     * @param capacity the capacity
     * @param number   the number
     * @return the hall
     */
    public Hall addHall(int capacity, int number){
        Hall hall = new Hall(capacity, number);
        hallRepository.save(hall);
        return hall;
    }

    /**
     * Get all halls list.
     *
     * @return the list
     */
    public List<Hall> getAllHalls(){
        return StreamSupport.stream(hallRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Find halls by capacity greater than list.
     *
     * @param capacity the capacity
     * @return the list
     */
    public List<Hall> findHallsByCapacityGreaterThan(int capacity){
        return hallRepository.findByCapacityGreaterThanEqual(capacity);
    }

    /**
     * Find halls by capacity list.
     *
     * @param capacity the capacity
     * @return the list
     */
    public List<Hall> findHallsByCapacity(int capacity){
        return hallRepository.findAllByCapacity(capacity);
    }
}
