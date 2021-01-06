package agh.alleviation.model.user;

import agh.alleviation.model.EntityObject;
import agh.alleviation.util.UserType;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.*;

/**
 * This abstract class represents any user of the system.
 * This class is specialized in specific subclasses.
 * User has a unique login and email. Name field represents name and surname (personal info) of the user.
 * Used inheritance strategy creates a joined table for the User type and specific tables for every extending class.
 *
 * @author Anna Nosek
 * @see Admin
 * @see Worker
 * @see Customer
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = User.TABLE_NAME)
public abstract class User extends EntityObject implements Externalizable {

    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "user";


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
         * The constant LOGIN.
         */
        public static final String LOGIN = "login";
        /**
         * The constant EMAIL.
         */
        public static final String EMAIL = "email";
        /**
         * The constant PASSWORD.
         */
        public static final String PASSWORD = "password";
    }

    /**
     * Instantiates a new User.
     */
    public User() {}

    /**
     * Instantiates a new User.
     *
     * @param name  name and surname of the user (personal info)
     * @param login unique login of the user
     * @param email email of the user
     */
    public User(final String name, final String login, final String email){
        setName(name);
        setLogin(login);
        setEmail(email);
        setIsActive(true);
    }

    /**
     * Instantiates a new User.
     *
     * @param name     name and surname of the user (personal info)
     * @param login    unique login of the user
     * @param email    email of the user
     * @param password user's password
     */
    public User(final String name, final String login, final String email, final String password){
        setName(name);
        setLogin(login);
        setEmail(email);
        setIsActive(true);
        this.password = password;
    }

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<UserType> userType = new SimpleObjectProperty<>(this, "usertype");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty login = new SimpleStringProperty(this, "login");
    private final StringProperty email = new SimpleStringProperty(this, "email");
    private String password;

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
     * @param newId the new id
     */
    public void setId(int newId) { id.set(newId);}

    /**
     * Id property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty idProperty() { return id; }


    /**
     * Gets user type.
     *
     * @return user type
     */
    public UserType getUserType(){
        return userTypeProperty().get();
    }

    /**
     * Sets user type.
     *
     * @param userType the user type
     */
    public void setUserType(UserType userType) {
        this.userType.set(userType);
    }

    /**
     * User type property object property.
     *
     * @return the object property
     */
    public ObjectProperty<UserType> userTypeProperty(){
        return this.userType;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    @Column(name = User.Columns.NAME, nullable = false, length = 50)
    public String getName(){
        return name.get();
    }

    /**
     * Set name.
     *
     * @param newName the new name
     */
    public void setName(String newName){ name.set(newName); }

    /**
     * Name property string property.
     *
     * @return the string property
     */
    public StringProperty nameProperty(){ return name; }


    /**
     * Get login string.
     *
     * @return the string
     */
    @Column(name = Columns.LOGIN, nullable = false, length = 50, unique = true)
    public String getLogin(){
        return login.get();
    }

    /**
     * Set login.
     *
     * @param newLogin the new login
     */
    public void setLogin(String newLogin){ login.set(newLogin); }

    /**
     * Login property string property.
     *
     * @return the string property
     */
    public StringProperty loginProperty(){ return login; }

    /**
     * Get email string.
     *
     * @return the string
     */
    @Column(name = Columns.EMAIL, nullable = false, length = 50, unique = true)
    public String getEmail(){
        return email.get();
    }

    /**
     * Set email.
     *
     * @param newEmail the new email
     */
    public void setEmail(String newEmail){ email.set(newEmail);}

    /**
     * Email property string property.
     *
     * @return the string property
     */
    public StringProperty emailProperty(){ return this.email; }

    /**
     * Get password.
     *
     * @return password string
     */
    @Column(name = Columns.PASSWORD)
    public String getPassword() {
        return password;
    }

    /**
     * Set new password.
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

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
