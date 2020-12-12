package agh.alleviation.service;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.model.Seance;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.Customer;
import agh.alleviation.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service responsible for manipulating order and ticket repositories.
 *
 * @author Ksenia Fiodarava
 * @see OrderRepository
 * @see Order
 * @see Ticket
 */
@Service
@Transactional
public class OrderService extends EntityObjectService<Order, OrderRepository> {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SeanceRepository seanceRepository;
    private final CustomerRepository customerRepository;

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository    the order repository
     * @param ticketRepository   the ticket repository
     * @param userRepository     the user repository
     * @param seanceRepository   the seance repository
     * @param customerRepository the customer repository
     */
    @Autowired
    public OrderService(
        OrderRepository orderRepository,
        TicketRepository ticketRepository,
        UserRepository userRepository,
        SeanceRepository seanceRepository,
        CustomerRepository customerRepository) {
        repository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.seanceRepository = seanceRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Add ticket.
     *
     * @param seance the seance
     * @param price  the price
     * @return the ticket
     */
    public Ticket addTicket(Seance seance, double price) {
        Ticket ticket = new Ticket(seance, price);
        seance = seanceRepository.findByIdWithTickets(seance.getId());
        seance.addTicket(ticket);
        ticketRepository.save(ticket);
        return ticket;
    }

    /**
     * Add order.
     *
     * @param tickets  the tickets
     * @param customer the customer
     * @return the order
     */
    public Order addOrder(List<Ticket> tickets, Customer customer) {
        Order order = new Order(customer);
        order.setTickets(tickets);
        customer = customerRepository.findByIdWithOrders(customer.getId());
        customer.addOrder(order);
        repository.save(order);
        userRepository.save(customer);
        return order;
    }

    /**
     * Get orders by customers.
     *
     * @param customer the customer
     * @return the list
     */
    public List<Order> getOrdersByCustomers(Customer customer) {
        return repository.findAllByCustomer(customer);
    }

    /**
     * Override method to get tickets associated with order
     * Because of lazy loading, they are not loaded at the object creation.
     *
     * @param order order to delete
     * @return list of entity objects deleted with order
     */
    public List<EntityObject> delete(EntityObject order) {
        order = repository.findByIdWithTickets(order.getId());
        return super.delete(order);
    }
}
