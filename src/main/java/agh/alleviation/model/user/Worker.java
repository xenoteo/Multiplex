package agh.alleviation.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Anna Nosek
 * This class represents the worker of the cinema.
 * Their fields do not differ from the standard user, although they will have different reponsibilities.
 */
@Entity
@Table(name = Worker.TABLE_NAME)
public class Worker extends User {
    public static final String TABLE_NAME = "worker";

}
