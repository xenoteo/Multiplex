package agh.alleviation.model;

import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        /**
         * The constant DATE.
         */
        public static final String DATE = "date";

    }

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    private final ObjectProperty<List<Ticket>> ticketsProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<User> userProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> dateProperty = new SimpleObjectProperty<>();

    /**
     * Instantiates a new Order.
     *
     * @param tickets the list of tickets
     * @param user    the user
     */
    public Order(List<Ticket> tickets, User user) {
        setTickets(tickets);
        setUser(user);
        setIsActive(true);
    }

    /**
     * Instantiates a new Order.
     *
     * @param user the user
     */
    public Order(User user) {
        setUser(user);
        setIsActive(true);
    }

    /**
     * Returns the ticket object property.
     *
     * @return the ticket object property
     */
    public ObjectProperty<List<Ticket>> ticketsProperty() { return ticketsProperty;}

    /**
     * Get the ticket list.
     *
     * @return the list of tickets
     */
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    public List<Ticket> getTickets() {
        return ticketsProperty.getValue();
    }

    /**
     * Gets the list of active tickets.
     *
     * @return the list of active tickets
     */
    @Transient
    public List<Ticket> getActiveTickets() {
        return ticketsProperty.getValue().stream().filter(Ticket::getIsActive).collect(Collectors.toList());
    }

    /**
     * Sets tickets.
     *
     * @param tickets the tickets
     */
    public void setTickets(List<Ticket> tickets) {
        ticketsProperty.setValue(tickets);
    }

    /**
     * Adds a ticket.
     *
     * @param ticket the ticket
     */
    public void addTicket(Ticket ticket){
        if(getTickets() == null){
            setTickets(new ArrayList<>());
        }
        getTickets().add(ticket);
    }


    /**
     * Return the user object property.
     *
     * @return the user object property
     */
    ObjectProperty<User> userProperty() { return userProperty;}

    /**
     * Gets the user.
     *
     * @return the user
     */
    @JoinColumn(name = Columns.USER)
    @ManyToOne
    public User getUser() {
        return userProperty.getValue();
    }

    /**
     * Sets a user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        userProperty.setValue(user);
    }

    /**
     * Gets a date.
     *
     * @return the date
     */
    public LocalDateTime getDate() {
        return dateProperty.getValue();
    }

    /**
     * Sets a date.
     *
     * @param date the date
     */
    public void setDate(LocalDateTime date) {
        dateProperty.setValue(date);
    }

    /**
     * Returns the date object property.
     *
     * @return the date object property
     */
    public ObjectProperty<LocalDateTime> dateProperty(){ return dateProperty; }

    @Override
    public List<EntityObject> update() {
        super.update();
        List<EntityObject> updatedObjects = new ArrayList<>(getTickets());
        getTickets().forEach(ticket -> {
            updatedObjects.addAll(ticket.update());
        });
        return updatedObjects;
    }

    @Override
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
        out.writeObject(getDate());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setTickets((List<Ticket>) in.readObject());
        setUser((Customer) in.readObject());
        setDate((LocalDateTime) in.readObject());
    }
}
