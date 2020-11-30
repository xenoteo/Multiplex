package agh.alleviation.model;

import agh.alleviation.model.user.Customer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * Class responsible for representation of customer's orders.
 * In one order there may be many tickets, but one order can be bought only by one user.
 *
 * @author Ksenia Fiodarava
 * @see Ticket
 * @see Customer
 */
@Entity
@Table(name = Order.TABLE_NAME)
public class Order implements Externalizable {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "order_table";

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant ID.
         */
        public static final String ID = "id";
        /**
         * The constant TICKETS.
         */
        public static final String TICKETS = "tickets";
        /**
         * The constant CUSTOMER.
         */
        public static final String CUSTOMER = "customer";
    }

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    private final IntegerProperty idProperty = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<List<Ticket>> ticketsProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Customer> customerProperty = new SimpleObjectProperty<>();


    /**
     * Instantiates a new Order.
     *
     * @param tickets  the tickets
     * @param customer the customer
     */
    public Order(List<Ticket> tickets, Customer customer){
        setTickets(tickets);
        setCustomer(customer);
    }


    /**
     * Id property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty idProperty() { return idProperty; }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Columns.ID)
    public int getId() {
        return idProperty.get();
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) { idProperty.set(id);}


    /**
     * Tickets property object property.
     *
     * @return the object property
     */
    ObjectProperty<List<Ticket>> ticketsProperty(){ return ticketsProperty;}

    /**
     * Get tickets list.
     *
     * @return the list
     */
    @Column(name = Columns.TICKETS)
    @OneToMany(fetch = FetchType.EAGER)
    public List<Ticket> getTickets(){
        return ticketsProperty.getValue();
    }

    /**
     * Set tickets.
     *
     * @param tickets the tickets
     */
    public void setTickets(List<Ticket> tickets){
        ticketsProperty.setValue(tickets);
    }


    /**
     * Customer property object property.
     *
     * @return the object property
     */
    ObjectProperty<Customer> customerProperty(){ return customerProperty;}

    /**
     * Get customer customer.
     *
     * @return the customer
     */
    @JoinColumn(name = Columns.CUSTOMER)
    @ManyToOne
    public Customer getCustomer(){
        return customerProperty.getValue();
    }

    /**
     * Set customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer){
        customerProperty.setValue(customer);
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeObject(getTickets());
        out.writeObject(getCustomer());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setTickets((List<Ticket>) in.readObject());
        setCustomer((Customer) in.readObject());
    }
}
