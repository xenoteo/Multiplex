package agh.alleviation.persistence;

import agh.alleviation.model.Ticket;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository of tickets.
 *
 * @see Ticket
 * @author Ksenia Fiodarava
 */
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

}
