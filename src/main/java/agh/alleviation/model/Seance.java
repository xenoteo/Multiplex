package agh.alleviation.model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDateTime;
import java.util.Date;
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
     * @param hall  the hall
     * @param date  the date
     * @param price the price
     */
    public Seance(Movie movie, Hall hall, LocalDateTime date, double price){
        setMovie(movie);
        setHall(hall);
        setDate(date);
        setPrice(price);
        setIsActive(true);
    }

    /**
     * Movie property object property.
     *
     * @return the object property
     */
    public ObjectProperty<Movie> movieProperty(){
        return movieProperty;
    }

    /**
     * Gets movie.
     *
     * @return the movie
     */
    @ManyToOne
    public Movie getMovie() {
        return movieProperty.getValue();
    }

    /**
     * Set movie.
     *
     * @param movie the movie
     */
    public void setMovie(Movie movie){
        movieProperty.setValue(movie);
    }


    /**
     * Hall property object property.
     *
     * @return the object property
     */
    public ObjectProperty<Hall> hallProperty(){
        return hallProperty;
    }

    /**
     * Gets hall.
     *
     * @return the hall
     */
    @ManyToOne
    public Hall getHall() {
        return hallProperty.getValue();
    }

    /**
     * Set hall.
     *
     * @param hall the hall
     */
    public void setHall(Hall hall){
        hallProperty.setValue(hall);
    }


    /**
     * Date property object property.
     *
     * @return the object property
     */
    public ObjectProperty<LocalDateTime> dateProperty(){
        return dateProperty;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    @Column(name = Columns.DATE)
    public LocalDateTime getDate() {
        return dateProperty.getValue();
    }

    /**
     * Set date.
     *
     * @param date the date
     */
    public void setDate(LocalDateTime date){
        dateProperty.setValue(date);
    }


    /**
     * Price property double property.
     *
     * @return the double property
     */
    public DoubleProperty priceProperty(){
        return priceProperty;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    @Column(name = Columns.PRICE)
    public double getPrice() {
        return priceProperty.get();
    }

    /**
     * Set price.
     *
     * @param price the price
     */
    public void setPrice(double price){
        priceProperty.set(price);
    }

    public ObjectProperty<List<Ticket>> ticketsProperty(){ return tickets; }

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST})
    public List<Ticket> getTickets(){ return tickets.get(); }

    public void setTickets(List<Ticket> tickets){ this.tickets.set(tickets);}


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeObject(getId());
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

    @Override
    public void delete(){
        super.delete();
        getTickets().forEach(Ticket::delete);
    }
}
