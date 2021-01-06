package agh.alleviation.model;

import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
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
public class Order extends EntityObject {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "order_table";

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant TICKETS.
         */
        public static final String TICKETS = "tickets";
        /**
         * The constant CUSTOMER.
         */
        public static final String USER = "user";
    }

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    private final ObjectProperty<List<Ticket>> ticketsProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<User> userProperty = new SimpleObjectProperty<>();

    /**
     * Instantiates a new Order.
     *
     * @param tickets  the tickets
     * @param customer the customer
     */
    public Order(List<Ticket> tickets, User user) {
        setTickets(tickets);
        setUser(user);
        setIsActive(true);
    }

    /**
     * Instantiates a new Order.
     *
     * @param customer the customer
     */
    public Order(User user) {
        setUser(user);
        setIsActive(true);
    }

    /**
     * Tickets property object property.
     *
     * @return the object property
     */
    public ObjectProperty<List<Ticket>> ticketsProperty() { return ticketsProperty;}

    /**
     * Get tickets list.
     *
     * @return the list
     */
//    @Column(name = Columns.TICKETS)
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Ticket> getTickets() {
        return ticketsProperty.getValue();
    }

    /**
     * Set tickets.
     *
     * @param tickets the tickets
     */
    public void setTickets(List<Ticket> tickets) {
        ticketsProperty.setValue(tickets);
    }

    public void addTicket(Ticket ticket) {
        if (getTickets() == null) {
            setTickets(new ArrayList<>());
        }
        getTickets().add(ticket);
    }

    /**
     * Customer property object property.
     *
     * @return the object property
     */
    ObjectProperty<User> userProperty() { return userProperty;}

    /**
     * Get customer customer.
     *
     * @return the customer
     */
    @JoinColumn(name = Columns.USER)
    @ManyToOne
    public User getUser() {
        return userProperty.getValue();
    }

    /**
     * Set customer.
     *
     * @param customer the customer
     */
    public void setUser(User user) {
        userProperty.setValue(user);
    }

    public List<EntityObject> update() {
        super.update();
        List<EntityObject> updatedObjects = new ArrayList<>(getTickets());
        getTickets().forEach(ticket -> {
            updatedObjects.addAll(ticket.update());
        });
        return updatedObjects;
    }

    public List<EntityObject> delete() {
        super.delete();
        List<EntityObject> deletedObjects = new ArrayList<>(getTickets());
        getTickets().forEach(ticket -> {
            deletedObjects.addAll(ticket.delete());
        });
        return deletedObjects;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(getTickets());
        out.writeObject(getUser());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setTickets((List<Ticket>) in.readObject());
        setUser((Customer) in.readObject());
    }
}
