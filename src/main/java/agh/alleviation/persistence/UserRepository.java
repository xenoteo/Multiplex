package agh.alleviation.persistence;

import agh.alleviation.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Repository of users.
 *
 * @author Anna Nosek
 * @see User
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Finds a user by login.
     *
     * @param login the login
     * @return the user
     */
    User findByLogin(String login);

    /**
     * Finds the list of all the users.
     *
     * @return the list of all the users
     */
    List<User> findAll();

    /**
     * Finds a user by email.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Finds a user by id with orders.
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = ?1")
    User findByIdWithOrders(int id);
}
