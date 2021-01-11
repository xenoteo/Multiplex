package agh.alleviation.model.user;

import agh.alleviation.util.UserType;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class represents the admin of the application. This will be the user with the widest set of responsibilities.
 *
 * @see User
 * @author Anna Nosek
 */
@Entity
@Table(name = Admin.TABLE_NAME)
public class Admin extends User {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "admin";

    /**
     * Instantiates a new Admin.
     */
    public Admin(){
        setUserType(UserType.ADMIN);
    }

    /**
     * Instantiates a new Admin.
     *
     * @param name  the name
     * @param login  the login
     * @param email  the email
     */
    public Admin(final String name, final String login, final String email){
        super(name, login, email);
    }
}
