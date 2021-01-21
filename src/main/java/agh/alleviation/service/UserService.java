package agh.alleviation.service;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.user.Admin;
import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import agh.alleviation.model.user.Worker;
import agh.alleviation.persistence.CustomerRepository;
import agh.alleviation.persistence.UserRepository;
import agh.alleviation.util.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service responsible for manipulating the user repository.
 *
 * @author Anna Nosek
 * @see User
 * @see UserRepository
 */
@Service
@Transactional
public class UserService extends EntityObjectService<User, UserRepository> {

    private final CustomerRepository customerRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository     the user repository
     * @param customerRepository the customer repository
     */
    @Autowired
    public UserService(UserRepository userRepository, CustomerRepository customerRepository) {
        repository = userRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * This function updates the userType in the user according to its concrete class.
     * UserType would be obsolete in the database, so it is only assigned when the User instance is accessed in the application.
     *
     * @param user  user from the database
     * @return user with updated UserType field
     * @see User
     */
    private User setUserType(User user) {
        if (user instanceof Admin) user.setUserType(UserType.ADMIN);
        else if (user instanceof Worker) user.setUserType(UserType.WORKER);
        else user.setUserType(UserType.CUSTOMER);
        return user;
    }

    /**
     * Adds new user to database.
     *
     * @param name  the name
     * @param login the login
     * @param email the email
     * @param type  the type
     * @return the user
     */
    public User addUser(String name, String login, String email, UserType type) {
        User newUser;

        newUser = switch (type) {
            case ADMIN -> new Admin(name, login, email);
            case WORKER -> new Worker(name, login, email);
            default -> new Customer(name, login, email);
        };

        newUser.setUserType(type);

        repository.save(newUser);

        return newUser;
    }

    /**
     * Adds new user to database.
     *
     * @param name     user's name
     * @param login    user's login
     * @param email    user's email
     * @param type     user's type
     * @param password user's password
     * @return instance of newly added user
     */
    public User addUser(String name, String login, String email, UserType type, String password) {
        User user = addUser(name, login, email, type);
        user.setPassword(password);
        repository.save(user);
        return user;
    }

    /**
     * Finds all customers list.
     *
     * @return the list of customers
     */
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Finds user by login.
     *
     * @param login the login
     * @return the user
     */
    public User findUserByLogin(String login) {
        User user = repository.findByLogin(login);
        if (user != null) setUserType(user);
        return user;
    }

    /**
     * Finds user by email.
     *
     * @param email the email
     * @return the user
     */
    public User findUserByEmail(String email) {
        User user = repository.findByEmail(email);
        if (user != null) setUserType(user);
        return user;
    }

    @Override
    public List<EntityObject> update(EntityObject user) {
        User userObj = repository.findByIdWithOrders(user.getId());
        return super.update(userObj);
    }

    /**
     * Overrides method to get orders associated with user if user is customer
     * Because of lazy loading, they are not loaded at the object creation.
     *
     * @param user  user to delete
     * @return list of entity objects deleted with user
     */
    @Override
    public List<EntityObject> delete(EntityObject user) {
        user = repository.findByIdWithOrders(user.getId());

        return super.delete(user);
    }


    /**
     * Validates whether input login and password are correct.
     *
     * @param login    provided login
     * @param password provided password
     * @return whether input data is correct
     */
    public boolean validateUser(String login, String password) {
        User user = findUserByLogin(login);
        if (user == null) return false;
        return password.equals(user.getPassword());
    }

    /**
     * Finds user with orders.
     *
     * @param user the user
     * @return the user
     */
    public User findUserWithOrders(User user){
        return repository.findByIdWithOrders(user.getId());
    }
}
