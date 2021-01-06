package agh.alleviation.persistence;

import agh.alleviation.model.Order;
import agh.alleviation.model.user.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository of cutomer's orders.
 *
 * @author Ksenia Fiodarava
 * @see Order
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
    /**
     * Find all by customer list.
     *
     * @param customer the customer
     * @return the list
     */
    List<Order> findAllByCustomer(Customer customer);

    /**
     * Find by id with tickets order.
     *
     * @param id the id
     * @return the order
     */
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.tickets WHERE o.id=?1")
    Order findByIdWithTickets(int id);
}
