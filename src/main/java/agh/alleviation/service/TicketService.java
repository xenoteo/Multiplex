package agh.alleviation.service;

import agh.alleviation.model.Ticket;
import agh.alleviation.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TicketService extends EntityObjectService<Ticket, TicketRepository> {
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.repository = ticketRepository;
    }
}
