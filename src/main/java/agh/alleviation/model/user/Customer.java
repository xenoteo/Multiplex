package agh.alleviation.model.user;

import javafx.beans.property.ObjectProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends User{
    public static final String TABLE_NAME = "customer";



}
