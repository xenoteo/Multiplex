package agh.alleviation.controller;

import agh.alleviation.model.*;
import agh.alleviation.model.user.User;
import agh.alleviation.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServiceManager {
    private final Map<Class<? extends EntityObject>, EntityObjectService<?, ?>> services;
    private final ObservableComposite observableComposite;

    @Autowired
    public ServiceManager(
        HallService hallService,
        MovieService movieService,
        SeanceService seanceService,
        UserService userService,
        TicketService ticketService) {

        this.services = new HashMap<>();
        this.services.put(Hall.class, hallService);
        this.services.put(Movie.class, movieService);
        this.services.put(Seance.class, seanceService);
        this.services.put(User.class, userService);
        this.services.put(Ticket.class, ticketService);

        this.observableComposite = new ObservableComposite();
    }

    private Class<?> getClassOf(EntityObject item) {
        Class<?> itemClass = item.getClass();
        if (item instanceof User) itemClass = itemClass.getSuperclass();
        return itemClass;
    }

    public ObservableList<EntityObject> getList(Class<? extends EntityObject> itemClass) {
        return observableComposite.getList(itemClass);
    }

    public ObservableList<EntityObject> getActiveElementsList(Class<? extends EntityObject> itemClass) {
        return observableComposite.getActiveElementsList(itemClass);
    }

    public EntityObjectService<?, ?> getService(Class<? extends EntityObject> itemClass) {
        return services.get(itemClass);
    }

    public void fillFromService(Class<? extends EntityObject> itemClass) {
        observableComposite.addAll(itemClass, services.get(itemClass).getAllActive());
    }

    public void add(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableComposite.addItem(itemClass, item);
        services.get(itemClass).add(item);
    }

    public void addToObservable(EntityObject item) {
        observableComposite.addItem(getClassOf(item), item);
    }

    public void update(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableComposite.update(itemClass, item);
        services.get(itemClass).update(item);
    }

    public void delete(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableComposite.delete(itemClass, item);
        List<EntityObject> deletedList = services.get(itemClass).delete(item);
        deletedList.forEach(this::update);
    }
}
