package agh.alleviation.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;

@Entity
@Table(name = Hall.TABLE_NAME)
public class Hall {
    public static final String TABLE_NAME = "hall";

    public static class Columns {
        public static final String ID = "id";
        public static final String CAPACITY = "capacity";
    }

    private final IntegerProperty idProperty = new SimpleIntegerProperty(this, "id");
    private final IntegerProperty capacityProperty = new SimpleIntegerProperty(this, "capacity");


    public Hall() {
    }

    public Hall(int capacity) {
        setCapacity(capacity);
    }


    public IntegerProperty idProperty() { return idProperty; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return idProperty.get();
    }

    public void setId(int id) { idProperty.set(id);}


    public IntegerProperty capacityProperty(){
        return capacityProperty;
    }

    @Column(name = Columns.CAPACITY)
    public int getCapacity() {
        return capacityProperty.get();
    }

    public void setCapacity(int capacity){
        capacityProperty.set(capacity);
    }
}