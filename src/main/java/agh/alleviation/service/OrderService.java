package agh.alleviation.service;

import agh.alleviation.model.Order;
import agh.alleviation.model.Seance;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.Customer;
import agh.alleviation.persistence.OrderRepository;
import agh.alleviation.persistence.TicketRepository;
import agh.alleviation.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Ksenia Fiodarava
 * Service responsible for manipulating order and ticket repositories.
 * @see OrderRepository
 * @see Order
 * @see Ticket
 */
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void addOrder(List<Ticket> tickets, Customer customer){
        Order order = new Order(tickets, customer);
        customer.addOrder(order);
        orderRepository.save(order);
        userRepository.save(customer);
    }

    public List<Order> getAllOrders(){
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Order> getOrdersByCustomers(Customer customer){
        return orderRepository.findAllByCustomer(customer);
    }


    public List<Ticket> getAllTickets(){
        return StreamSupport.stream(ticketRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void addTicket(Seance seance, double price){
        Ticket ticket = new Ticket(seance, price);
        ticketRepository.save(ticket);
    }
}
