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
import java.util.Objects;

@Entity
@Table(name = Movie.TABLE_NAME)
public class Movie implements Externalizable {
    public static final String TABLE_NAME = "movie";

    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public Movie() { }

    public Movie(final String name) {
        setName(name);
    }


    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return id.get();
    }

    public void setId(int id) { this.id.set(id);}

    public IntegerProperty idProperty() { return id; }


    private final StringProperty name = new SimpleStringProperty(this, "name");

    @Column(name = Movie.Columns.NAME, nullable = false, length = 50, unique = true)
    public String getName() {
        return name.get();
    }

    public void setName(String name) { this.name.set(name); }

    public StringProperty nameProperty() { return name; }

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

/*


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && name.equals(movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

 */