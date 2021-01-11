package agh.alleviation.model.user;

import agh.alleviation.util.UserType;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class represents the worker of the cinema.
 * Their fields do not differ from the standard user, although they will have different responsibilities.
 *
 * @see User
 * @author Anna Nosek
 */
@Entity
@Table(name = Worker.TABLE_NAME)
public class Worker extends User {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "worker";

    /**
     * Instantiates a new Worker.
     */
    public Worker() {
        setUserType(UserType.WORKER);
    }

    /**
     * Instantiates a new Worker.
     *
     * @param name  the name
     * @param login the login
     * @param email the email
     */
    public Worker(final String name, final String login, final String email) {
        super(name, login, email);
    }
}
