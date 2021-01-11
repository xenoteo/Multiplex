package agh.alleviation.model.user;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.util.UserType;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.*;
import java.util.List;

/**
 * This abstract class represents any user of the system.
 * This class is specialized in specific subclasses.
 * User has a unique login and email. Name field represents name and surname (personal info) of the user.
 * Used inheritance strategy creates a joined table for the User type and specific tables for every extending class.
 *
 * @author Anna Nosek
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
    private final ObjectProperty<List<Order>> ordersProperty = new SimpleObjectProperty<>();

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
     * @param newId  the new id
     */
    public void setId(int newId) { id.set(newId);}

    /**
     * Returns the integer id property.
     *
     * @return the id property
     */
    public IntegerProperty idProperty() { return id; }


    /**
     * Gets the user type.
     *
     * @return the user type
     */
    public UserType getUserType(){
        return userTypeProperty().get();
    }

    /**
     * Sets the user type.
     *
     * @param userType the user type
     */
    public void setUserType(UserType userType) {
        this.userType.set(userType);
    }

    /**
     * Returns the UserType object property.
     *
     * @return the UserType property
     */
    public ObjectProperty<UserType> userTypeProperty(){
        return this.userType;
    }

    /**
     * Gets the name string.
     *
     * @return the name string
     */
    @Column(name = User.Columns.NAME, nullable = false, length = 50)
    public String getName(){
        return name.get();
    }

    /**
     * Sets the name.
     *
     * @param newName the new name
     */
    public void setName(String newName){ name.set(newName); }

    /**
     * Returns the name string property.
     *
     * @return the name property
     */
    public StringProperty nameProperty(){ return name; }


    /**
     * Gets the login string.
     *
     * @return the login string
     */
    @Column(name = Columns.LOGIN, nullable = false, length = 50, unique = true)
    public String getLogin(){
        return login.get();
    }

    /**
     * Sets the login.
     *
     * @param newLogin the new login
     */
    public void setLogin(String newLogin){ login.set(newLogin); }

    /**
     * Returns the login string property.
     *
     * @return the login string property
     */
    public StringProperty loginProperty(){ return login; }

    /**
     * Gets the email string.
     *
     * @return the email string
     */
    @Column(name = Columns.EMAIL, nullable = false, length = 50, unique = true)
    public String getEmail(){
        return email.get();
    }

    /**
     * Sets the email.
     *
     * @param newEmail the new email
     */
    public void setEmail(String newEmail){ email.set(newEmail);}

    /**
     * Returns the email string property.
     *
     * @return the email string property
     */
    public StringProperty emailProperty(){ return this.email; }

    /**
     * Gets the password.
     *
     * @return the password string
     */
    @Column(name = Columns.PASSWORD)
    public String getPassword() {
        return password;
    }

    /**
     * Sets the new password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Gets the order list.
     *
     * @return the order list
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    public List<Order> getOrders() {
        return ordersProperty.get();
    }

    /**
     * Sets orders.
     *
     * @param orders the orders
     */
    public void setOrders(List<Order> orders) {
        ordersProperty.set(orders);
    }

    /**
     * Returns the orders object property.
     *
     * @return the orders object property
     */
    public ObjectProperty<List<Order>> ordersProperty() {
        return ordersProperty;
    }


    /**
     * Adds an order.
     *
     * @param order the order
     */
    public void addOrder(Order order) {
        getOrders().add(order);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeObject(getName());
        out.writeObject(getLogin());
        out.writeObject(getEmail());
        out.writeObject(getOrders());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setName((String) in.readObject());
        setLogin((String) in.readObject());
        setEmail((String) in.readObject());
        setOrders((List<Order>) in.readObject());
    }
}
