package agh.alleviation.persistence;

import agh.alleviation.model.Seance;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository of seances.
 *
 * @author Ksenia Fiodarava
 * @see Seance
 */
public interface SeanceRepository extends CrudRepository<Seance, Integer> {
}
