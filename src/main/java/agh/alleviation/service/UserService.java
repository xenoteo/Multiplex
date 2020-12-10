package agh.alleviation.service;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Seance;
import agh.alleviation.model.user.Admin;
import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import agh.alleviation.model.user.Worker;
import agh.alleviation.persistence.CustomerRepository;
import agh.alleviation.persistence.HallRepository;
import agh.alleviation.persistence.UserRepository;
import agh.alleviation.util.UserType;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    public UserService(UserRepository userRepository, CustomerRepository customerRepository){
        this.repository = userRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * This function updates the userType in the user according to its concrete class.
     * UserType would be obsolete in the database, so it is only assigned when the User instance is accessed in the application.
     * @see User
     * @param user user from the database
     * @return user with updated UserType field
     */
    private User setUserType(User user){
        if(user instanceof Admin) user.setUserType(UserType.ADMIN);
        else if(user instanceof Worker) user.setUserType(UserType.WORKER);
        else user.setUserType(UserType.CUSTOMER);
        return user;
    }

    /**
     * Add user user.
     *
     * @param name  the name
     * @param login the login
     * @param email the email
     * @param type  the type
     * @return the user
     */
    public User addUser(String name, String login, String email, UserType type){
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

    public void updateUser(User user) {
        repository.save(user);
    }

    /**
     * Get all users list.
     *
     * @return the list
     */
    public List<User> getAllUsers(){
        return repository.findAll().stream().map(this::setUserType).collect(Collectors.toList());
    }

    /**
     * Get all customers list.
     *
     * @return the list
     */
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    /**
     * Get user by login user.
     *
     * @param login the login
     * @return the user
     */
    public User getUserByLogin(String login){
        return setUserType(repository.findByLogin(login));
    }

}
