package agh.alleviation.service;

import agh.alleviation.model.Hall;
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
 * @author Anna Nosek
 * Service responsible for manipulating the user repository.
 * @see User
 * @see UserRepository
 */

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public UserService(UserRepository userRepository, CustomerRepository customerRepository){
        this.userRepository = userRepository;
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

    public User addUser(String name, String login, String email, UserType type){
        User newUser;

        newUser = switch (type) {
            case ADMIN -> new Admin();
            case WORKER -> new Worker();
            default -> new Customer();
        };

        newUser.setUserType(type);
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setLogin(login);

        userRepository.save(newUser);

        return newUser;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll().stream().map(this::setUserType).collect(Collectors.toList());
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public User getUserByLogin(String login){
        return setUserType(userRepository.findByLogin(login));
    }


}
