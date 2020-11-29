package agh.alleviation.service;

import agh.alleviation.model.*;
import agh.alleviation.persistence.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Ksenia Fiodarava
 * Service responsible for manipulating the seance repository.
 * @see SeanceRepository
 * @see Seance
 */
@Service
@Transactional
public class SeanceService {
    private final SeanceRepository seanceRepository;

    @Autowired
    public SeanceService(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    public void addSeance(Movie movie, Hall hall, Date date, double price){
        Seance seance = new Seance(movie, hall, date, price);
        seanceRepository.save(seance);
    }

    public List<Seance> getAllSeances(){
        return StreamSupport.stream(seanceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
