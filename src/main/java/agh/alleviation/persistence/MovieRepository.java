package agh.alleviation.persistence;

import agh.alleviation.model.Movie;
import io.reactivex.rxjava3.core.Observable;
import javafx.collections.ObservableList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    Movie findByName(String name);

    List<Movie> findAll();
}

