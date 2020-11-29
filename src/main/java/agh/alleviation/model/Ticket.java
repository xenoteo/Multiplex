package agh.alleviation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

import javax.persistence.*;

@Entity
@Table(name = Ticket.TABLE_NAME)
public class Ticket {
    public static final String TABLE_NAME = "ticket";

    public static class Columns {
        public static final String ID = "id";
        public static final String SEANCE = "seance";
        public static final String PRICE = "price";
    }

    private final IntegerProperty idProperty = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<Seance> seanceProperty = new SimpleObjectProperty<>();
    private final DoubleProperty priceProperty = new SimpleDoubleProperty(this, "price");


    public Ticket() {
    }

    public Ticket(Seance seance, double price) {
        setSeance(seance);
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


    public ObjectProperty<Seance> seanceProperty() { return seanceProperty; }

    @ManyToOne
    public Seance getSeance() {
        return seanceProperty.getValue();
    }

    public void setSeance(Seance seance) { seanceProperty.setValue(seance);}


    public DoubleProperty priceProperty() { return priceProperty; }

    @Column(name = Columns.PRICE)
    public double getPrice(){
        return priceProperty.get();
    }

    public void setPrice(double price){
        priceProperty.set(price);
    }
}
