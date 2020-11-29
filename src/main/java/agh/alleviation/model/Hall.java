package agh.alleviation.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Ksenia Fiodarava
 * Class responsible for representation of cinema hall. It keeps the number of a hall and its capacity.
 */
@Entity
@Table(name = Hall.TABLE_NAME)
public class Hall implements Externalizable {
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


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeInt(getCapacity());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setCapacity(in.readInt());
    }
}