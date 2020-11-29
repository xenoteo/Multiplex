package agh.alleviation.persistence;

import agh.alleviation.model.Seance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeanceRepository extends CrudRepository<Seance, Integer> {
}
