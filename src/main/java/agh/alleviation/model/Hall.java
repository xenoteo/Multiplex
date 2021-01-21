package agh.alleviation.model;

import javafx.beans.property.*;

import javax.persistence.*;
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

        /**
         * The constant SEANCES.
         */
        public static final String SEANCES = "seances";
    }

    private final IntegerProperty capacityProperty = new SimpleIntegerProperty(this, Columns.CAPACITY);
    private final IntegerProperty numberProperty = new SimpleIntegerProperty(this, Columns.NUMBER);
    private final ObjectProperty<List<Seance>> seancesProperty = new SimpleObjectProperty<>(this, Columns.SEANCES);

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
     * Returns the capacity integer property.
     *
     * @return the capacity integer property
     */
    public IntegerProperty capacityProperty() {
        return capacityProperty;
    }

    /**
     * Gets the capacity.
     *
     * @return the capacity
     */
    @Column(name = Columns.CAPACITY)
    public int getCapacity() {
        return capacityProperty.get();
    }

    /**
     * Sets the capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        capacityProperty.set(capacity);
    }

    /**
     * Returns the number integer property.
     *
     * @return the integer property
     */
    public IntegerProperty numberProperty() { return numberProperty; }

    /**
     * Gets the hall's number.
     *
     * @return the hall's number.
     */
    @Column(name = Columns.NUMBER)
    public int getNumber() { return numberProperty.get(); }

    /**
     * Set the hall's number.
     *
     * @param number the hall's number
     */
    public void setNumber(int number) { numberProperty.set(number); }

    /**
     * Gets seances.
     *
     * @return the seances
     */
    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST})
    public List<Seance> getSeances() { return seancesProperty.get(); }

    /**
     * Sets seances.
     *
     * @param seances the seances
     */
    public void setSeances(List<Seance> seances) { seancesProperty.set(seances); }

    /**
     * Adds a seance.
     *
     * @param seance the seance
     */
    public void addSeance(Seance seance) { getSeances().add(seance); }

    @Override
    public List<EntityObject> update() {
        super.update();
        List<EntityObject> updatedObjects = new ArrayList<>(getSeances());
        getSeances().forEach(seance -> {
            updatedObjects.addAll(seance.update());
        });
        return updatedObjects;
    }

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

    @Override
    public String toString() {
        return "Hall " + getNumber();
    }
}