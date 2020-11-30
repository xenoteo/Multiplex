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
public class Genre implements Externalizable {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "genre";

    /**
     * The type Columns.
     */
    public static class Columns{
        /**
         * The constant ID.
         */
        public static final String ID = "id";
        /**
         * The constant NAME.
         */
        public static final String NAME = "name";
    }


    private final IntegerProperty id = new SimpleIntegerProperty(this, Columns.ID);
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
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return id.get();
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) { this.id.set(id); }

    /**
     * Id property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty idProperty() { return id; }


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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeObject(getName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setName((String) in.readObject());
    }
}
