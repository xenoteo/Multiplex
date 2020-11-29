package agh.alleviation.persistence;

import agh.alleviation.model.Ticket;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Ksenia Fiodarava
 * Repository of tickets.
 * @see Ticket
 */
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
}
