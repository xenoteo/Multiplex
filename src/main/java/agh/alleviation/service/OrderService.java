package agh.alleviation.service;

import agh.alleviation.model.*;
import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import agh.alleviation.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final OrderRepository orderRepository;

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
        this.orderRepository = orderRepository;
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

    public Ticket addTicketToOrder(Seance seance, double price, Order order) {
        Ticket ticket = addTicket(seance, price);
        order.getTickets().add(ticket);
        ticket.setOrder(order);
        repository.save(order);
        ticketRepository.save(ticket);
        return ticket;
    }

    public Order addOrder(Customer customer) {
        return addOrder(new ArrayList<>(), customer);
    }

    /**
     * Add order.
     *
     * @param tickets  the tickets
     * @param customer the customer
     * @return the order
     */
    public Order addOrder(List<Ticket> tickets, User user) {
        Order order = new Order(user);
        order.setTickets(tickets);
        user = userRepository.findByIdWithOrders(user.getId());
        user.addOrder(order);
        repository.save(order);
        userRepository.save(user);
        return order;
    }

    @Override
    public void add(EntityObject order) {
        Order orderObj = (Order) order;
        User user = userRepository.findByIdWithOrders((orderObj.getUser().getId()));
        user.addOrder(orderObj);
        orderRepository.save(orderObj);
        userRepository.save(user);

        HashMap<Seance, List<Ticket>> seancesAndTicket = new HashMap<>();

        for (Ticket ticket : orderObj.getTickets()) {
            ticket.setOrder(orderObj);
            seancesAndTicket.computeIfAbsent(ticket.getSeance(), k -> new ArrayList<>());
            seancesAndTicket.get(ticket.getSeance()).add(ticket);
            ticketRepository.save(ticket);


//            Seance loadedSeance = seanceRepository.findByIdWithTickets(seance.getId());
//            loadedSeance.addTicket(seancesAndTicket.get(seance));
//            seanceRepository.save(seance);
        }

//        System.out.println("OrderService::add");
        for(Seance seance: seancesAndTicket.keySet()){
            System.out.println(seance.getId());
            Seance loadedSeance = seanceRepository.findByIdWithTickets(seance.getId());
            seancesAndTicket.get(loadedSeance).forEach(loadedSeance::addTicket);
            seanceRepository.save(loadedSeance);
        }
    }

    /**
     * Get orders by customers.
     *
     * @param customer the customer
     * @return the list
     */
    public List<Order> getOrdersByUser(User user) {
        return repository.findAllByUser(user);
    }

    public List<EntityObject> update(EntityObject order) {
        repository.save((Order) order);
        order = repository.findByIdWithTickets(order.getId());
        return super.update(order);
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


    public Order getOrderWithTickets(Order order){
        return repository.findByIdWithTickets(order.getId());
    }
}
