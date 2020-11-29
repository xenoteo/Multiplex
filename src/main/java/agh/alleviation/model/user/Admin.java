package agh.alleviation.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Anna Nosek
 * This class represents the admin of the application.
 * This will be the user with the widest set of responsibilities.
 */
@Entity
@Table(name = Admin.TABLE_NAME)
public class Admin extends User {
    public static final String TABLE_NAME = "admin";
}
