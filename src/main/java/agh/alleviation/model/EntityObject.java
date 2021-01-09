package agh.alleviation.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.*;
import javax.swing.border.EtchedBorder;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An abstraction for all database Entities. The EntityObject class itself is not an entity in the database, hence the @MappedSuperclass annotation.
 * Allows generalization of higher-level code.
 *
 * @author Anna Nosek
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntityObject implements Externalizable {

    /**
     * Instantiates a new Entity object.
     */
    public EntityObject() {
    }

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant ID.
         */
        public static final String ID = "id";
        /**
         * The constant IS_ACTIVE.
         */
        public static final String IS_ACTIVE = "is_active";
    }

    private int id;
    private final BooleanProperty isActive = new SimpleBooleanProperty(this, Columns.IS_ACTIVE);

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
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
        return id;
    }

    /**
     * Gets is active.
     *
     * @return the is active
     */
    @Column(name = Columns.IS_ACTIVE)
    public Boolean getIsActive() { return isActive.get(); }

    /**
     * Sets is active.
     *
     * @param isActive the is active
     */
    public void setIsActive(boolean isActive) { this.isActive.set(isActive);}

    /**
     * Is active property boolean property.
     *
     * @return the boolean property
     */
    @Transient
    public BooleanProperty isActiveProperty() { return isActive; }

    /**
     * Update
     *
     * @return list of affected items
     */
    public List<EntityObject> update() {
        List<EntityObject> updatedObjects = new ArrayList<>();
        updatedObjects.add(this);
        return updatedObjects;
    }

    /**
     * Delete list.
     *
     * @return the list
     */
    public List<EntityObject> delete() {
        System.out.println("Deleting " + this.getClass());
        List<EntityObject> deletedObjects = new ArrayList<>();
        isActive.set(false);
        deletedObjects.add(this);
        return deletedObjects;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeBoolean(getIsActive());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setIsActive(in.readBoolean());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityObject that = (EntityObject) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
