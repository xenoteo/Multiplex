package agh.alleviation.controller;

import agh.alleviation.model.*;
import agh.alleviation.model.user.User;
import agh.alleviation.service.*;
import io.reactivex.rxjava3.core.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ObservableComposite {

    private final Map<Class<? extends EntityObject>, ObservableList<EntityObject>> observableLists;
    private final Map<Class<? extends EntityObject>, EntityObjectService<?, ?>> services;

    @Autowired
    public ObservableComposite(
        HallService hallService,
        MovieService movieService,
        SeanceService seanceService,
        UserService userService,
        TicketService ticketService) {

        this.observableLists = new HashMap<>();
        this.services = new HashMap<>();
        this.services.put(Hall.class, hallService);
        this.services.put(Movie.class, movieService);
        this.services.put(Seance.class, seanceService);
        this.services.put(User.class, userService);
        this.services.put(Ticket.class, ticketService);

        this.observableLists.put(Ticket.class, FXCollections.observableArrayList());
    }

    private Class<?> getClassOf(EntityObject item) {
        Class<?> itemClass = item.getClass();
        if (item instanceof User) itemClass = itemClass.getSuperclass();
        return itemClass;
    }

    public void addObservableList(Class<? extends EntityObject> itemClass) {
        observableLists.put(itemClass, FXCollections.observableArrayList());
    }

    public void add(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableLists.get(itemClass).add(item);
        services.get(itemClass).add(item);
    }

    public ObservableList<EntityObject> getList(Class<? extends EntityObject> itemClass) {
        return observableLists.get(itemClass);
    }

    public ObservableList<EntityObject> getActiveElementsList(Class<? extends EntityObject> itemClass) {
        return observableLists.get(itemClass).filtered(EntityObject::getIsActive);
    }

    public void addAll(List<EntityObject> items) {
        observableLists.get(getClassOf(items.get(0))).addAll(items);
        //add through service
    }

    public void fillFromService(Class<? extends EntityObject> itemClass) {
        addObservableList(itemClass);
        observableLists.get(itemClass).addAll(services.get(itemClass).getAllActive());
    }

    public void update(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        var list = observableLists.get(itemClass);
        if (!list.contains(item)) list.add(item);
        observableLists.get(itemClass).remove(item);
        observableLists.get(itemClass).add(item);
        services.get(itemClass).update(item);
    }

    public EntityObjectService<?, ?> getService(Class<? extends EntityObject> itemClass) {
        return services.get(itemClass);
    }

    public void addToObservable(EntityObject item) {
        observableLists.get(getClassOf(item)).add(item);
    }

    public void delete(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableLists.get(itemClass).remove(item);
        List<EntityObject> deletedList = services.get(itemClass).delete(item);
        deletedList.forEach(this::update);
    }

}
