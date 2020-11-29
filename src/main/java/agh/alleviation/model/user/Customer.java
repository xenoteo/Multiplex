package agh.alleviation.model.user;

import javafx.beans.property.ObjectProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Anna Nosek
 * This class represents the customer of the cinema.
 * Their fields do not differ from the standard user, although they will have the ability to create orders.
 */
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends User{
    public static final String TABLE_NAME = "customer";



}
