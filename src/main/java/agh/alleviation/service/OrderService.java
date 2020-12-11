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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
public class OrderService extends EntityObjectService<Order, OrderRepository>{
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SeanceRepository seanceRepository;
    private final CustomerRepository customerRepository;

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository  the order repository
     * @param ticketRepository the ticket repository
     * @param userRepository   the user repository
     */
    @Autowired
    public OrderService(OrderRepository orderRepository, TicketRepository ticketRepository, UserRepository userRepository, SeanceRepository seanceRepository, CustomerRepository customerRepository) {
        this.repository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.seanceRepository = seanceRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Add order.
     *
     * @param tickets  the tickets
     * @param customer the customer
     */
    public Order addOrder(List<Ticket> tickets, Customer customer){
        Order order = new Order(customer);
        order.setTickets(tickets);
        customer = customerRepository.findByIdWithOrders(customer.getId());
        customer.addOrder(order);
        repository.save(order);
        userRepository.save(customer);
        return order;
    }

    /**
     * Get all orders list.
     *
     * @return the list
     */
    public List<Order> getAllOrders(){
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get orders by customers list.
     *
     * @param customer the customer
     * @return the list
     */
    public List<Order> getOrdersByCustomers(Customer customer){
        return repository.findAllByCustomer(customer);
    }


    /**
     * Get all tickets list.
     *
     * @return the list
     */
    public List<Ticket> getAllTickets(){
        return StreamSupport.stream(ticketRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Add ticket ticket.
     *
     * @param seance the seance
     * @param price  the price
     * @return the ticket
     */
    public Ticket addTicket(Seance seance, double price){
        Ticket ticket = new Ticket(seance, price);
        seance = seanceRepository.findByIdWithTickets(seance.getId());
        seance.addTicket(ticket);
        ticketRepository.save(ticket);
        return ticket;
    }

    public void delete(EntityObject order){
        order = repository.findByIdWithTickets(order.getId());
        super.delete(order);
    }


}
