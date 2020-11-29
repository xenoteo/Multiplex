package agh.alleviation.service;

import agh.alleviation.model.user.Admin;
import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import agh.alleviation.model.user.Worker;
import agh.alleviation.persistence.UserRepository;
import agh.alleviation.util.UserType;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser(String name, String login, String email, UserType type){
        User newUser;

        newUser = switch (type){
            case ADMIN -> new Admin();
            case WORKER -> new Worker();
            default -> new Customer();
        };

        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setLogin(login);

        userRepository.save(newUser);

        return newUser;

    }

    public Observable<User> getAllUsers(){
        return Observable.fromIterable(userRepository.findAll());
    }

    public User getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }




}
