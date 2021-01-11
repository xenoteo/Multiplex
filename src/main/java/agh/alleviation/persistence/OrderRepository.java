package agh.alleviation.persistence;

import agh.alleviation.model.Order;
import agh.alleviation.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository of user's orders.
 *
 * @see Order
 * @author Ksenia Fiodarava
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
    /**
     * Finds the list of orders all by user.
     *
     * @param user  the user
     * @return the list
     */
    List<Order> findAllByUser(User user);

    /**
     * Finds an order by id with tickets.
     *
     * @param id  the id
     * @return the order
     */
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.tickets WHERE o.id=?1")
    Order findByIdWithTickets(int id);
}
