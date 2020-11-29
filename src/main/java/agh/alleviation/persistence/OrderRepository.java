package agh.alleviation.persistence;

import agh.alleviation.model.Order;
import agh.alleviation.model.user.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Ksenia Fiodarava
 * Repository of cutomer's orders.
 * @see Order
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAllByCustomer(Customer customer);
}
