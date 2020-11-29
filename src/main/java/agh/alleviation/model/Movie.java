package agh.alleviation.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kamil Krzempek
 * @author Anna Nosek
 * This class is responsible for storing information about movies.
 * It stores the name of the movie, its genre, director and a short list of actors.
 * Actors are kept in a coma separated String. MovieService will be responsible for processing this list into a useful structure.
 * @see Genre
 */
@Entity
@Table(name = Movie.TABLE_NAME)
public class Movie implements Externalizable {
    public static final String TABLE_NAME = "movie";

    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String GENRE = "genre";
        public static final String DIRECTOR = "director";
        public static final String ACTORS = "actors";
    }

    public Movie() { }

    public Movie(final String name, Genre genre) {
        setName(name);
        setGenre(genre);
    }

    private final IntegerProperty id = new SimpleIntegerProperty(this, Columns.ID);
    private final StringProperty name = new SimpleStringProperty(this, Columns.NAME);
    private final StringProperty description = new SimpleStringProperty(this, Columns.DESCRIPTION);
    private final ObjectProperty<Genre> genre = new SimpleObjectProperty<>(this, Columns.GENRE);
    private final StringProperty director = new SimpleStringProperty(this, Columns.DIRECTOR);
    private final StringProperty actors = new SimpleStringProperty(this, Columns.ACTORS);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return id.get();
    }

    public void setId(int id) { this.id.set(id); }

    public IntegerProperty idProperty() { return id; }

    @Column(name = Movie.Columns.NAME, nullable = false, length = 50, unique = true)
    public String getName() {
        return name.get();
    }

    public void setName(String name) { this.name.set(name); }

    public StringProperty nameProperty() { return name; }

    @Column(name = Columns.DESCRIPTION)
    public String getDescription(){ return description.get(); }

    public void setDescription(String description){ this.description.set(description);}

    public StringProperty descriptionProperty(){ return description; }

    @OneToOne
    public Genre getGenre(){ return genre.get(); }

    public void setGenre(Genre genre){ this.genre.set(genre); }

    public ObjectProperty<Genre> genreProperty(){ return genre; }

    @Column(name = Columns.DIRECTOR)
    public String getDirector(){ return director.get(); }

    public void setDirector(String director){ this.director.set(director);}

    public StringProperty directorProperty(){ return director; }

    @Column(name = Columns.ACTORS)
    public String getActors(){ return actors.get(); }

    public void setActors(String actors){ this.actors.set(actors); }

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
}
