package agh.alleviation.presentation.context.manager;

import agh.alleviation.model.*;
import agh.alleviation.model.user.User;
import agh.alleviation.service.*;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides logic and abstraction of Controller-Service-ObservableList communication.
 * Introduced to decouple Controllers from Services and to allow showing the current database changes made in different parts of the application.
 * @author Anna Nosek
 */
@Component
public class ServiceManager {
    private final Map<Class<? extends EntityObject>, EntityObjectService<?, ?>> services;
    private final ObservableComposite observableComposite;

    /**
     * Instantiates a new Service manager.
     *
     * @param hallService   the hall service
     * @param movieService  the movie service
     * @param seanceService the seance service
     * @param userService   the user service
     * @param ticketService the ticket service
     */
    @Autowired
    public ServiceManager(
        HallService hallService,
        MovieService movieService,
        SeanceService seanceService,
        UserService userService,
        TicketService ticketService,
        OrderService orderService) {

        this.services = new HashMap<>();
        this.services.put(Hall.class, hallService);
        this.services.put(Movie.class, movieService);
        this.services.put(Seance.class, seanceService);
        this.services.put(User.class, userService);
        this.services.put(Ticket.class, ticketService);
        this.services.put(Order.class, orderService);

        this.observableComposite = new ObservableComposite();
    }

    /**
     * A helper function.
     * @param item - database item
     * @return - conrete class of the item, with exception of User, for which the abstract class User is returned
     */
    private Class<?> getClassOf(EntityObject item) {
        Class<?> itemClass = item.getClass();
        if (item instanceof User) itemClass = itemClass.getSuperclass();
        return itemClass;
    }

    /**
     * Gets an ObservableList associated with a specialization of EntityObject class.
     *
     * @param itemClass the class of desired ObservableList's item
     * @return desired ObservableList
     */
    public ObservableList<EntityObject> getList(Class<? extends EntityObject> itemClass) {
        return observableComposite.getList(itemClass);
    }

    /**
     * Gets ObservableList of specific active EntityObjects
     *
     * @param itemClass the class of desired ObservableList's item
     * @return the active elements ObservableList
     */
    public ObservableList<EntityObject> getActiveElementsList(Class<? extends EntityObject> itemClass) {
        return observableComposite.getActiveElementsList(itemClass);
    }

    /**
     * Gets a service for a specific class of EntityObject
     *
     * @param itemClass the item class
     * @return the service
     */
    public EntityObjectService<?, ?> getService(Class<? extends EntityObject> itemClass) {
        return services.get(itemClass);
    }

    /**
     * Fill ObservableList of specified class with all active
     *
     * @param itemClass the item class
     */
    public void fillFromService(Class<? extends EntityObject> itemClass) {
        observableComposite.addAll(itemClass, services.get(itemClass).getAll());
    }

    /**
     * Adds the EntityObject to the ObservableList (through ObservableComposite) and the database (through Service).
     *
     * @param item the item to be added
     */
    public void add(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableComposite.add(itemClass, item);
        services.get(itemClass).add(item);
    }

    /**
     * Add to observable.
     *
     * @param item the item
     */
    public void addToObservable(EntityObject item) {
        observableComposite.add(getClassOf(item), item);
    }

    public void deleteFromObservable(EntityObject item) {
        observableComposite.delete(getClassOf(item), item);
    }

    /**
     * Update.
     *
     * @param item the item
     */
    public void update(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableComposite.update(itemClass, item);
        services.get(itemClass).update(item);
    }

    /**
     * Delete - deletes the object from database (here deleting is understood as marking as not active)
     * and delegates the update of the affected objects in the ObservableLists.
     *
     * @param item the item
     */
    public void delete(EntityObject item) {
        Class<?> itemClass = getClassOf(item);
        observableComposite.delete(itemClass, item);
        List<EntityObject> deletedList = services.get(itemClass).delete(item);
        deletedList.forEach(this::update);
    }

    public void clearObservableList(Class<? extends EntityObject> itemClass){
        observableComposite.clearObservableList(itemClass);

    }
}
