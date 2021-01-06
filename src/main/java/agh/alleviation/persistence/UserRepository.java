package agh.alleviation.persistence;

import agh.alleviation.model.user.Customer;
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
     * Find by login user.
     *
     * @param login the login
     * @return the user
     */
    User findByLogin(String login);

    List<User> findAll();

    /**
     * Finds user by email.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = ?1")
    User findByIdWithOrders(int id);



}
