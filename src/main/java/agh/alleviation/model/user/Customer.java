package agh.alleviation.model.user;

import agh.alleviation.model.Order;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * @author Anna Nosek
 * This class represents the customer of the cinema.
 * Their fields do not differ from the standard user, although they will have the ability to create orders.
 */
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends User{
    public static final String TABLE_NAME = "customer";


    private final ObjectProperty<List<Order>> ordersProperty = new SimpleObjectProperty<>();

    @OneToMany(fetch = FetchType.EAGER)
    public List<Order> getOrders(){
        return ordersProperty.get();
    }

    public void setOrders(List<Order> orders){
        ordersProperty.set(orders);
    }

    public ObjectProperty<List<Order>> ordersProperty(){
        return ordersProperty;
    }

    public void addOrder(Order order){
        getOrders().add(order);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(getOrders());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setOrders((List<Order>) in.readObject());

    }


}
