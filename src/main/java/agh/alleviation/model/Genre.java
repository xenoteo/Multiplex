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
 * @author Anna Nosek
 * This class represents movie genres.
 */
@Entity
@Table(name=Genre.TABLE_NAME)
public class Genre implements Externalizable {
    public static final String TABLE_NAME = "genre";

    public static class Columns{
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    private final IntegerProperty id = new SimpleIntegerProperty(this, Columns.ID);
    private final StringProperty name = new SimpleStringProperty(this, Columns.NAME);


    public Genre(){

    }

    public Genre(String name){
        setName(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return id.get();
    }

    public void setId(int id) { this.id.set(id); }

    public IntegerProperty idProperty() { return id; }


    @Column(name = Columns.NAME)
    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public StringProperty nameProperty(){ return  this.name; }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
