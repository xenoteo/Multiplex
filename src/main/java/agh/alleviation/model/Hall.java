package agh.alleviation.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Class responsible for representation of cinema hall. It keeps the number of a hall and its capacity.
 *
 * @author Ksenia Fiodarava
 *
 * */
@Entity
@Table(name = Hall.TABLE_NAME)
public class Hall extends EntityObject{
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
    }

    private final IntegerProperty capacityProperty = new SimpleIntegerProperty(this, "capacity");
    private final IntegerProperty numberProperty = new SimpleIntegerProperty(this, "number");


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
    public IntegerProperty capacityProperty(){
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
    public void setCapacity(int capacity){
        capacityProperty.set(capacity);
    }

    /**
     * Number property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty numberProperty(){ return this.numberProperty; }

    /**
     * Get number int.
     *
     * @return the int
     */
    @Column(name = Columns.NUMBER)
    public int getNumber(){ return numberProperty.get(); }

    /**
     * Set number.
     *
     * @param number the number
     */
    public void setNumber(int number){ numberProperty.set(number); }

    @Override
    public String toString() {
        return "Hall " + getNumber();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
//        out.writeInt(getId());
        out.writeInt(getCapacity());
        out.writeInt(getNumber());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
//        setId(in.readInt());
        setCapacity(in.readInt());
        setCapacity(in.readInt());
    }
}