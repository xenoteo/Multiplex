package agh.alleviation.model.user;

import agh.alleviation.model.Order;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

//    @OneToMany
//    List<Order> getOrders(){
//        retur
//    }


}
