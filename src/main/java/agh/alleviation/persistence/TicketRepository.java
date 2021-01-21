package agh.alleviation.persistence;

import agh.alleviation.model.Ticket;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository of tickets.
 *
 * @author Ksenia Fiodarava
 * @see Ticket
 */
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

}
