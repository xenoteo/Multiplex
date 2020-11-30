package agh.alleviation.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class represents the worker of the cinema.
 * Their fields do not differ from the standard user, although they will have different responsibilities.
 *
 * @author Anna Nosek
 * @see User
 */
@Entity
@Table(name = Worker.TABLE_NAME)
public class Worker extends User {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "worker";

}
