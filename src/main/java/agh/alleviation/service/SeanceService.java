package agh.alleviation.service;

import agh.alleviation.model.*;
import agh.alleviation.persistence.HallRepository;
import agh.alleviation.persistence.MovieRepository;
import agh.alleviation.persistence.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service responsible for manipulating the seance repository.
 *
 * @see SeanceRepository
 * @see Seance
 * @author Ksenia Fiodarava
 */
@Service
@Transactional
public class SeanceService extends EntityObjectService<Seance, SeanceRepository> {
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    /**
     * Instantiates a new Seance service.
     *
     * @param seanceRepository  the seance repository
     * @param movieRepository  the movie repository
     * @param hallRepository  the hall repository
     */
    @Autowired
    public SeanceService(
        SeanceRepository seanceRepository, MovieRepository movieRepository, HallRepository hallRepository) {
        repository = seanceRepository;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
    }

    /**
     * Adds a seance.
     *
     * @param movie  the movie
     * @return the seance
     */
    public Seance addSeance(Movie movie) {
        Seance seance = new Seance(movie);
        movie = movieRepository.findByIdWithSeances(movie.getId());
        movie.addSeance(seance);
        repository.save(seance);
        return seance;
    }

    /**
     * Adds a seance.
     *
     * @param movie  the movie
     * @param hall  the hall
     * @param date  the date
     * @param price  the price
     * @return the seance
     */
    public Seance addSeance(Movie movie, Hall hall, LocalDateTime date, double price) {
        Seance seance = addSeance(movie);
        seance.setHall(hall);
        seance.setDate(date);
        seance.setPrice(price);
        hall = hallRepository.findByIdWithSeances(hall.getId());
        hall.addSeance(seance);
        repository.save(seance);
        hallRepository.save(hall);
        return seance;
    }

    @Override
    public List<EntityObject> update(EntityObject seance) {
        repository.save((Seance) seance);
        seance = repository.findByIdWithTickets(seance.getId());
        return super.update(seance);
    }

    /**
     * Overrides method to get tickets associated with seance
     * Because of lazy loading, they are not loaded at the object creation.
     *
     * @param seance seance to delete
     * @return list of entity objects deleted with seance
     */
    @Override
    public List<EntityObject> delete(EntityObject seance) {
        Seance seanceObj = repository.findByIdWithTickets(seance.getId());
        return super.delete(seanceObj);
    }
}
