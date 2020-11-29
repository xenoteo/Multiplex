package agh.alleviation.service;

import agh.alleviation.model.Order;
import agh.alleviation.model.Seance;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.Customer;
import agh.alleviation.persistence.OrderRepository;
import agh.alleviation.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
    }

    public void addOrder(List<Ticket> tickets, Customer customer){
        Order order = new Order(tickets, customer);
        orderRepository.save(order);
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

    public Ticket addTicket(Seance seance, double price){
        Ticket ticket = new Ticket(seance, price);
        ticketRepository.save(ticket);
        return ticket;
    }
}
