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
 * @author Ksenia Fiodarava
 * Service responsible for manipulating the hall repository.
 * @see HallRepository
 * @see Hall
 */
@Service
@Transactional
public class HallService {
    private final HallRepository hallRepository;

    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public void addHall(int capacity){
        Hall hall = new Hall(capacity);
        hallRepository.save(hall);
    }

    public List<Hall> getAllHalls(){
        return StreamSupport.stream(hallRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Hall> findHallsByCapacityGreaterThan(int capacity){
        return hallRepository.findByCapacityGreaterThanEqual(capacity);
    }

    public List<Hall> findHallsByCapacity(int capacity){
        return hallRepository.findAllByCapacity(capacity);
    }
}
