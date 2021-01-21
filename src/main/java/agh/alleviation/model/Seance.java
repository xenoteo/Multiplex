package agh.alleviation.model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for representation of a movie's seance.
 * It keeps the information about the movie, the hall where seance takes place, the seance's date and its price.
 * One movie can have many seance, but a seance can be assigned only to one movie.
 * In one hall there can take place many seances, but a seance can be assigned only to one hall.
 *
 * @author Ksenia Fiodarava
 * @see Movie
 * @see Hall
 */
@Entity
@Table(name = Seance.TABLE_NAME)
public class Seance extends EntityObject {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "seance";

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant MOVIE.
         */
        public static final String MOVIE = "movie";
        /**
         * The constant HALL.
         */
        public static final String HALL = "hall";
        /**
         * The constant DATE.
         */
        public static final String DATE = "date";
        /**
         * The constant PRICE.
         */
        public static final String PRICE = "price";
    }

    private final ObjectProperty<Movie> movieProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Hall> hallProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> dateProperty = new SimpleObjectProperty<>();
    private final DoubleProperty priceProperty = new SimpleDoubleProperty(this, "price");
    private final ObjectProperty<List<Ticket>> tickets = new SimpleObjectProperty<>();

    /**
     * Instantiates a new Seance.
     */
    public Seance() {
    }

    /**
     * Instantiates a new Seance.
     *
     * @param movie the movie
     */
    public Seance(Movie movie) {
        setMovie(movie);
        setTickets(new ArrayList<>());
        setIsActive(true);
    }

    /**
     * Instantiates a new Seance.
     *
     * @param movie the movie
     * @param hall  the hall
     * @param date  the date
     * @param price the price
     */
    public Seance(Movie movie, Hall hall, LocalDateTime date, double price) {
        this(movie);
        setHall(hall);
        setDate(date);
        setPrice(price);
    }

    /**
     * Returns the movie object property.
     *
     * @return the movie object property
     */
    public ObjectProperty<Movie> movieProperty() {
        return movieProperty;
    }

    /**
     * Gets the movie.
     *
     * @return the movie
     */
    @ManyToOne
    public Movie getMovie() {
        return movieProperty.getValue();
    }

    /**
     * Sets the movie.
     *
     * @param movie the movie
     */
    public void setMovie(Movie movie) {
        movieProperty.setValue(movie);
    }

    /**
     * Returns the hall object property.
     *
     * @return the hall object property
     */
    public ObjectProperty<Hall> hallProperty() {
        return hallProperty;
    }

    /**
     * Gets the hall.
     *
     * @return the hall
     */
    @ManyToOne
    public Hall getHall() {
        return hallProperty.getValue();
    }

    /**
     * Sets the hall.
     *
     * @param hall the hall
     */
    public void setHall(Hall hall) {
        hallProperty.setValue(hall);
    }

    /**
     * Returns the date object property.
     *
     * @return the date object property
     */
    public ObjectProperty<LocalDateTime> dateProperty() {
        return dateProperty;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    @Column(name = Columns.DATE)
    public LocalDateTime getDate() {
        return dateProperty.getValue();
    }

    /**
     * Sets the date.
     *
     * @param date the date
     */
    public void setDate(LocalDateTime date) {
        dateProperty.setValue(date);
    }

    /**
     * Returns the price double property.
     *
     * @return the price double property
     */
    public DoubleProperty priceProperty() {
        return priceProperty;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    @Column(name = Columns.PRICE)
    public double getPrice() {
        return priceProperty.get();
    }

    /**
     * Sets the price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        priceProperty.set(price);
    }

    /**
     * Returns the ticket list object property.
     *
     * @return the ticket list object property
     */
    public ObjectProperty<List<Ticket>> ticketsProperty() { return tickets; }

    /**
     * Gets tickets.
     *
     * @return the tickets
     */
    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST})
    public List<Ticket> getTickets() { return tickets.get(); }

    /**
     * Sets tickets.
     *
     * @param tickets the tickets
     */
    public void setTickets(List<Ticket> tickets) { this.tickets.set(tickets);}

    /**
     * Adds a ticket.
     *
     * @param ticket the ticket
     */
    public void addTicket(Ticket ticket) {
        getTickets().add(ticket);
    }

    @Override
    public List<EntityObject> update() {
        super.update();
        List<EntityObject> updatedTickets = new ArrayList<>(getTickets());
        getTickets().forEach(ticket -> {
            updatedTickets.addAll(ticket.update());
        });
        return updatedTickets;
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
        out.writeObject(getMovie());
        out.writeObject(getDate());
        out.writeObject(getPrice());
        out.writeObject(getTickets());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setMovie((Movie) in.readObject());
        setDate((LocalDateTime) in.readObject());
        setPrice(in.readDouble());
        setTickets((List<Ticket>) in.readObject());
    }
}
