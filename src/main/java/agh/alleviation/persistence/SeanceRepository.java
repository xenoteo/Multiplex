package agh.alleviation.persistence;

import agh.alleviation.model.Seance;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Ksenia Fiodarava
 * Repository of seances.
 * @see Seance
 */
public interface SeanceRepository extends CrudRepository<Seance, Integer> {
}
