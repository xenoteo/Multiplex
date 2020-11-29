package agh.alleviation.model.user;

import agh.alleviation.util.UserType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.*;

/**
 * @author Anna Nosek
 * This abstract class represents any user of the system. This class is specialized in specific subclasses.
 * Used inheritance stategies include
 * @see Admin
 * @see Worker
 * @see Customer
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = User.TABLE_NAME)
public abstract class User implements Externalizable {

    public static final String TABLE_NAME = "user";


    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String LOGIN = "login";
        public static final String EMAIL = "email";
    }

    public User() {}

    /**
     * @param name name and surname of the user (personal info)
     * @param login unique login of the user
     * @param email email of the user
     */
    public User(final String name, final String login, final String email){
        setName(name);
        setLogin(login);
        setEmail(email);
    }


    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    public int getId() {
        return id.get();
    }

    public void setId(int newId) { id.set(newId);}

    public IntegerProperty idProperty() { return id; }


    private UserType userType;

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    private final StringProperty name = new SimpleStringProperty(this, "name");

    @Column(name = User.Columns.NAME, nullable = false, length = 50)
    public String getName(){
        return name.get();
    }

    public void setName(String newName){ name.set(newName); }

    public StringProperty nameProperty(){ return name; }

    private final StringProperty login = new SimpleStringProperty(this, "login");

    @Column(name = Columns.LOGIN, nullable = false, length = 50, unique = true)
    public String getLogin(){
        return login.get();
    }

    public void setLogin(String newLogin){ login.set(newLogin); }

    public StringProperty loginProperty(){ return login; }

    private final StringProperty email = new SimpleStringProperty(this, "email");

    @Column(name = Columns.EMAIL, nullable = false, length = 50, unique = true)
    public String getEmail(){
        return email.get();
    }

    public void setEmail(String newEmail){ email.set(newEmail);}

    public StringProperty emailProperty(){ return loginProperty(); }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeObject(getName());
        out.writeObject(getLogin());
        out.writeObject(getEmail());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setName((String) in.readObject());
        setLogin((String) in.readObject());
        setEmail((String) in.readObject());

    }

}
