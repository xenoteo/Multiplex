package agh.alleviation.persistence;

import agh.alleviation.model.Ticket;
import org.springframework.data.repository.CrudRepository;


public interface TicketRepository extends CrudRepository<Ticket, Integer> {
}
