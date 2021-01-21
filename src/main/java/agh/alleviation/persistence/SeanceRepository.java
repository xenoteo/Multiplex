package agh.alleviation.persistence;

import agh.alleviation.model.Seance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository of seances.
 *
 * @author Ksenia Fiodarava
 * @see Seance
 */
public interface SeanceRepository extends CrudRepository<Seance, Integer> {
    /**
     * Finds a seance by id with tickets.
     *
     * @param id the id
     * @return the seance
     */
    @Query("SELECT s FROM Seance s LEFT JOIN FETCH s.tickets WHERE s.id = ?1")
    Seance findByIdWithTickets(int id);
}
