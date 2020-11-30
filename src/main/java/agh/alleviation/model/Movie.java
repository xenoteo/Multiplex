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
 * This class is responsible for storing information about movies.
 * It stores the name of the movie, its genre, director and a short list of actors.
 * Actors are kept in a coma separated String.
 * MovieService will be responsible for processing this list into a useful structure.
 *
 * @author Kamil Krzempek
 * @author Anna Nosek
 * @see Genre
 */
@Entity
@Table(name = Movie.TABLE_NAME)
public class Movie implements Externalizable {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "movie";

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant ID.
         */
        public static final String ID = "id";
        /**
         * The constant NAME.
         */
        public static final String NAME = "name";
        /**
         * The constant DESCRIPTION.
         */
        public static final String DESCRIPTION = "description";
        /**
         * The constant GENRE.
         */
        public static final String GENRE = "genre";
        /**
         * The constant DIRECTOR.
         */
        public static final String DIRECTOR = "director";
        /**
         * The constant ACTORS.
         */
        public static final String ACTORS = "actors";
    }

    /**
     * Instantiates a new Movie.
     */
    public Movie() { }

    /**
     * Instantiates a new Movie.
     *
     * @param name  the name
     * @param genre the genre
     */
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
     * Gets name.
     *
     * @return the name
     */
    @Column(name = Movie.Columns.NAME, nullable = false, length = 50, unique = true)
    public String getName() {
        return name.get();
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) { this.name.set(name); }

    /**
     * Name property string property.
     *
     * @return the string property
     */
    public StringProperty nameProperty() { return name; }

    /**
     * Get description string.
     *
     * @return the string
     */
    @Column(name = Columns.DESCRIPTION)
    public String getDescription(){ return description.get(); }

    /**
     * Set description.
     *
     * @param description the description
     */
    public void setDescription(String description){ this.description.set(description);}

    /**
     * Description property string property.
     *
     * @return the string property
     */
    public StringProperty descriptionProperty(){ return description; }

    /**
     * Get genre genre.
     *
     * @return the genre
     */
    @OneToOne
    public Genre getGenre(){ return genre.get(); }

    /**
     * Set genre.
     *
     * @param genre the genre
     */
    public void setGenre(Genre genre){ this.genre.set(genre); }

    /**
     * Genre property object property.
     *
     * @return the object property
     */
    public ObjectProperty<Genre> genreProperty(){ return genre; }

    /**
     * Get director string.
     *
     * @return the string
     */
    @Column(name = Columns.DIRECTOR)
    public String getDirector(){ return director.get(); }

    /**
     * Set director.
     *
     * @param director the director
     */
    public void setDirector(String director){ this.director.set(director);}

    /**
     * Director property string property.
     *
     * @return the string property
     */
    public StringProperty directorProperty(){ return director; }

    /**
     * Get actors string.
     *
     * @return the string
     */
    @Column(name = Columns.ACTORS)
    public String getActors(){ return actors.get(); }

    /**
     * Set actors.
     *
     * @param actors the actors
     */
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
