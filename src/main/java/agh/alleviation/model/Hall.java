package agh.alleviation.model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for representation of cinema hall. It keeps the number of a hall and its capacity.
 *
 * @author Ksenia Fiodarava
 */
@Entity
@Table(name = Hall.TABLE_NAME)
public class Hall extends EntityObject {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "hall";

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant CAPACITY.
         */
        public static final String CAPACITY = "capacity";
        /**
         * The constant NUMBER.
         */
        public static final String NUMBER = "number";

        public static final String SEANCES = "seances";
    }

    private final IntegerProperty capacityProperty = new SimpleIntegerProperty(this, Columns.CAPACITY);
    private final IntegerProperty numberProperty = new SimpleIntegerProperty(this, Columns.NUMBER);
    private final ObjectProperty<List<Seance>> seancesProperty = new SimpleObjectProperty<>(this, Columns.SEANCES);
//

    /**
     * Instantiates a new Hall.
     */
    public Hall() {
    }

    /**
     * Instantiates a new Hall.
     *
     * @param capacity the capacity
     * @param number   the number
     */
    public Hall(int capacity, int number) {
        setCapacity(capacity);
        setNumber(number);
        setIsActive(true);
    }

    /**
     * Capacity property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty capacityProperty() {
        return capacityProperty;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    @Column(name = Columns.CAPACITY)
    public int getCapacity() {
        return capacityProperty.get();
    }

    /**
     * Set capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        capacityProperty.set(capacity);
    }

    /**
     * Number property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty numberProperty() { return this.numberProperty; }

    /**
     * Get number int.
     *
     * @return the int
     */
    @Column(name = Columns.NUMBER)
    public int getNumber() { return numberProperty.get(); }

    /**
     * Set number.
     *
     * @param number the number
     */
    public void setNumber(int number) { numberProperty.set(number); }

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST})
    public List<Seance> getSeances() { return seancesProperty.get(); }

    public void setSeances(List<Seance> seances) { seancesProperty.set(seances); }

    public void addSeance(Seance seance) { this.getSeances().add(seance); }

    @Override
    public List<EntityObject> delete() {
        super.delete();
        List<EntityObject> deletedObjects = new ArrayList<>(getSeances());
        getSeances().forEach(seance -> {
            deletedObjects.addAll(seance.delete());
        });
        return deletedObjects;
    }

    @Override
    public String toString() {
        return "Hall " + getNumber();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(getCapacity());
        out.writeInt(getNumber());
        out.writeObject(getSeances());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setCapacity(in.readInt());
        setCapacity(in.readInt());
        setSeances((List<Seance>) in.readObject());
    }
}