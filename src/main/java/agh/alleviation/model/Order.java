package agh.alleviation.model;

import agh.alleviation.model.user.Customer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order {
    public static final String TABLE_NAME = "ticket";

    public static class Columns {
        public static final String ID = "id";
        public static final String TICKETS = "tickets";
        public static final String CUSTOMER = "customer";
    }

    private final IntegerProperty idProperty = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<List<Ticket>> ticketsProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Customer> customerProperty = new SimpleObjectProperty<>();


    public Order() {
    }

    public Order(List<Ticket> tickets, Customer customer){
        setTickets(tickets);
        setCustomer(customer);
    }


    public IntegerProperty idProperty() { return idProperty; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return idProperty.get();
    }

    public void setId(int id) { idProperty.set(id);}


    ObjectProperty<List<Ticket>> ticketsProperty(){ return ticketsProperty;}

    @Column(name = Columns.TICKETS)
    @OneToMany(fetch = FetchType.EAGER)
    public List<Ticket> getTickets(){
        return ticketsProperty.getValue();
    }

    public void setTickets(List<Ticket> tickets){
        ticketsProperty.setValue(tickets);
    }


    ObjectProperty<Customer> customerProperty(){ return customerProperty;}

    @ManyToOne
    public Customer getCustomer(){
        return customerProperty.getValue();
    }

    public void setCustomer(Customer customer){
        customerProperty.setValue(customer);
    }
}
