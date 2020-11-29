package agh.alleviation.service;

import agh.alleviation.model.user.Admin;
import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import agh.alleviation.model.user.Worker;
import agh.alleviation.persistence.UserRepository;
import agh.alleviation.util.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addUser(String name, String login, String email, UserType type){
        User newUser;

        switch (type){
            case ADMIN: newUser = new Admin();
            case WORKER: newUser = new Worker();
            default: newUser = new Customer();
        }

        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setLogin(login);

        userRepository.save(newUser);

    }

    public User getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }




}
