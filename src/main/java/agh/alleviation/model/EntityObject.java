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

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntityObject implements Externalizable {

    public EntityObject() {
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String IS_ACTIVE = "is_active";
    }

    private int id;
    private final BooleanProperty isActive = new SimpleBooleanProperty(this, Columns.IS_ACTIVE);

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return id;
    }

    @Column(name = Columns.IS_ACTIVE)
    public Boolean getIsActive() { return isActive.get(); }

    public void setIsActive(boolean isActive) { this.isActive.set(isActive);}

    @Transient
    public BooleanProperty isActiveProperty() { return isActive; }

    public List<EntityObject> delete() {
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
