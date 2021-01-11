package agh.alleviation.model.user;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.util.UserType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the customer of the cinema.
 * Their fields do not differ from the standard user, although they will have the ability to create orders.
 *
 * @see User
 * @author Anna Nosek
 */
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends User {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "customer";

    /**
     * Instantiates a new Customer.
     */
    public Customer() {
        setUserType(UserType.CUSTOMER);
    }

    /**
     * Instantiates a new Customer.
     *
     * @param name  the name
     * @param login  the login
     * @param email  the email
     */
    public Customer(final String name, final String login, final String email) {
        super(name, login, email);
    }

    @Override
    public List<EntityObject> update() {
        super.update();
        List<EntityObject> updatedObjects = new ArrayList<>(getOrders());
        getOrders().forEach(order -> {
            updatedObjects.addAll(order.update());
        });
        return updatedObjects;
    }

    @Override
    public List<EntityObject> delete() {
        super.delete();
        List<EntityObject> deletedObjects = new ArrayList<>(getOrders());
        getOrders().forEach(order -> {
            deletedObjects.addAll(order.delete());
        });
        return deletedObjects;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
}
