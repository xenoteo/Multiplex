package agh.alleviation.model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

/**
 * @author Ksenia Fiodarava
 * Class responsible for representation of a movie's seance. It keeps the information about the movie, the hall where seance takes place, the seance's date and its price.
 * One movie can have many seance, but a seance can be assigned only to one movie.
 * In one hall there can take place many seances, but a seance can be assigned only to one hall.
 * @see Movie
 * @see Hall
 */
@Entity
@Table(name = Seance.TABLE_NAME)
public class Seance implements Externalizable {
    public static final String TABLE_NAME = "seance";



    public static class Columns {
        public static final String ID = "id";
        public static final String MOVIE = "movie";
        public static final String HALL = "hall";
        public static final String DATE = "date";
        public static final String PRICE = "price";
    }

    private final IntegerProperty idProperty = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<Movie> movieProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Hall> hallProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Date> dateProperty = new SimpleObjectProperty<>();
    private final DoubleProperty priceProperty = new SimpleDoubleProperty(this, "price");


    public Seance() {

    }

    public Seance(Movie movie, Hall hall, Date date, double price){
        setMovie(movie);
        setHall(hall);
        setDate(date);
        setPrice(price);
    }


    public IntegerProperty idProperty() { return idProperty; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return idProperty.get();
    }

    public void setId(int id) { idProperty.set(id);}


    ObjectProperty<Movie> movieProperty(){
        return movieProperty;
    }

    @ManyToOne
    public Movie getMovie() {
        return movieProperty.getValue();
    }

    public void setMovie(Movie movie){
        movieProperty.setValue(movie);
    }


    ObjectProperty<Hall> hallProperty(){
        return hallProperty;
    }

    @ManyToOne
    public Hall getHall() {
        return hallProperty.getValue();
    }

    public void setHall(Hall hall){
        hallProperty.setValue(hall);
    }


    ObjectProperty<Date> dateProperty(){
        return dateProperty;
    }

    @Column(name = Columns.DATE)
    public Date getDate() {
        return dateProperty.getValue();
    }

    public void setDate(Date date){
        dateProperty.setValue(date);
    }


    public DoubleProperty priceProperty(){
        return priceProperty;
    }

    @Column(name = Columns.PRICE)
    public double getPrice() {
        return priceProperty.get();
    }

    public void setPrice(double price){
        priceProperty.set(price);
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getId());
        out.writeObject(getMovie());
        out.writeObject(getDate());
        out.writeObject(getPrice());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setMovie((Movie) in.readObject());
        setDate((Date) in.readObject());
        setPrice(in.readDouble());

    }
}
