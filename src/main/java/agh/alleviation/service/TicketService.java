package agh.alleviation.service;

import agh.alleviation.model.Ticket;
import agh.alleviation.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Stub of service for manipulating Ticket repository
 *
 * @author Kamil Krzempek
 * @see TicketRepository
 * @see Ticket
 */
@Service
@Transactional
public class TicketService extends EntityObjectService<Ticket, TicketRepository> {
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        repository = ticketRepository;
    }
}
