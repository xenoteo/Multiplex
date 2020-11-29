package agh.alleviation.persistence;

import agh.alleviation.model.Movie;
import agh.alleviation.model.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByLogin(String login);

    List<User> findAll();


}
