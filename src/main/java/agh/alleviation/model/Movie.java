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
public class Movie extends EntityObject {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "movie";

    /**
     * The type Columns.
     */
    public static class Columns {
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
     * @param name        the name
     * @param genre       the genre
     * @param description the description
     * @param director    the director
     * @param actors      the actors
     */
    public Movie(final String name, Genre genre, String description, String director, String actors) {
        setName(name);
        setGenre(genre);
        setDescription(description);
        setDirector(director);
        setActors(actors);
        setIsActive(true);
    }

    private final StringProperty name = new SimpleStringProperty(this, Columns.NAME);
    private final StringProperty description = new SimpleStringProperty(this, Columns.DESCRIPTION);
    private final ObjectProperty<Genre> genre = new SimpleObjectProperty<>(this, Columns.GENRE);
    private final StringProperty director = new SimpleStringProperty(this, Columns.DIRECTOR);
    private final StringProperty actors = new SimpleStringProperty(this, Columns.ACTORS);
    private final ObjectProperty<List<Seance>> seances = new SimpleObjectProperty<>();

    /**
     * Gets name.
     *
     * @return the name
     */
    @Column(name = Movie.Columns.NAME, nullable = false, length = 50, unique = false)
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
    public String getDescription() { return description.get(); }

    /**
     * Set description.
     *
     * @param description the description
     */
    public void setDescription(String description) { this.description.set(description);}

    /**
     * Description property string property.
     *
     * @return the string property
     */
    public StringProperty descriptionProperty() { return description; }

    /**
     * Get genre genre.
     *
     * @return the genre
     */
    @OneToOne
    public Genre getGenre() { return genre.get(); }

    /**
     * Set genre.
     *
     * @param genre the genre
     */
    public void setGenre(Genre genre) { this.genre.set(genre); }

    /**
     * Genre property object property.
     *
     * @return the object property
     */
    public ObjectProperty<Genre> genreProperty() { return genre; }

    /**
     * Get director string.
     *
     * @return the string
     */
    @Column(name = Columns.DIRECTOR)
    public String getDirector() { return director.get(); }

    /**
     * Set director.
     *
     * @param director the director
     */
    public void setDirector(String director) { this.director.set(director);}

    /**
     * Director property string property.
     *
     * @return the string property
     */
    public StringProperty directorProperty() { return director; }

    /**
     * Get actors string.
     *
     * @return the string
     */
    @Column(name = Columns.ACTORS)
    public String getActors() { return actors.get(); }

    /**
     * Set actors.
     *
     * @param actors the actors
     */
    public void setActors(String actors) { this.actors.set(actors); }

    /**
     * Actors property string property.
     *
     * @return the string property
     */
    public StringProperty actorsProperty() { return actors; }

    /**
     * Gets seances.
     *
     * @return the seances
     */
    @OneToMany(cascade = {CascadeType.PERSIST})
    public List<Seance> getSeances() { return seances.get(); }

    /**
     * Sets seances.
     *
     * @param seances the seances
     */
    public void setSeances(List<Seance> seances) { this.seances.set(seances); }

    /**
     * Seances property object property.
     *
     * @return the object property
     */
    public ObjectProperty<List<Seance>> seancesProperty() { return seances; }

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

    /**
     * Add seance.
     *
     * @param seance the seance
     */
    public void addSeance(Seance seance) {
        getSeances().add(seance);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(getName());
        out.writeObject(getSeances());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setName((String) in.readObject());
        setSeances((List<Seance>) seances);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getId() == movie.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
