package agh.alleviation.persistence;

import agh.alleviation.model.user.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Customer repository, might be later changed to a specialization of user repository.
 *
 * @author Anna Nosek
 * @see Customer
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findAll();
}
