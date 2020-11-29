package agh.alleviation.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Worker.TABLE_NAME)
public class Worker extends User {
    public static final String TABLE_NAME = "worker";

}
