package agh.alleviation.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * This class represents movie genres.
 *
 * @author Anna Nosek
 * @see Movie
 */
@Entity
@Table(name=Genre.TABLE_NAME)
public class Genre extends EntityObject  {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "genre";

    /**
     * The type Columns.
     */
    public static class Columns{
        /**
         * The constant NAME.
         */
        public static final String NAME = "name";
    }


    private final StringProperty name = new SimpleStringProperty(this, Columns.NAME);


    /**
     * Instantiates a new Genre.
     */
    public Genre(){

    }

    /**
     * Instantiates a new Genre.
     *
     * @param name the name
     */
    public Genre(String name){

        setName(name);
        setIsActive(true);
    }


    /**
     * Get name string.
     *
     * @return the string
     */
    @Column(name = Columns.NAME)
    public String getName(){
        return name.get();
    }

    /**
     * Set name.
     *
     * @param name the name
     */
    public void setName(String name){
        this.name.set(name);
    }

    /**
     * Name property string property.
     *
     * @return the string property
     */
    public StringProperty nameProperty(){ return  this.name; }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
//        out.writeInt(getId());
        out.writeObject(getName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
//        setId(in.readInt());
        setName((String) in.readObject());
    }
}
