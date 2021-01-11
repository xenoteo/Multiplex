package agh.alleviation.presentation.context.manager;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Composite of all ObservableLists of the ListControllers. Provides logic of adding, deleting and updating elements of the ObservableLists.
 *
 * @author Anna Nosek
 * @author Kamil Krzempek
 */
public class ObservableComposite {
    private final Map<Class<?>, ObservableList<EntityObject>> observableLists;

    /**
     * Instantiates a new Observable composite.
     */
    public ObservableComposite() {
        observableLists = new HashMap<>();

        observableLists.put(Ticket.class, FXCollections.observableArrayList());
    }

    /**
     * Gets the list.
     *
     * @param itemClass the item class
     * @return the list
     */
    public ObservableList<EntityObject> getList(Class<?> itemClass) {
        return observableLists.get(itemClass);
    }

    /**
     * Gets the active elements list.
     *
     * @param itemClass the item class
     * @return the active elements list
     */
    public ObservableList<EntityObject> getActiveElementsList(Class<?> itemClass) {
        return observableLists.get(itemClass).filtered(EntityObject::getIsActive);
    }

    /**
     * Adds an observable list.
     *
     * @param itemClass the item class
     */
    public void addObservableList(Class<?> itemClass) {
        observableLists.put(itemClass, FXCollections.observableArrayList());
    }

    /**
     * Adds an item.
     *
     * @param itemClass the item class
     * @param item      the item
     */
    public void add(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        observableLists.get(itemClass).add(item);
    }

    /**
     * Adds all.
     *
     * @param itemClass the item class
     * @param itemList  the item list
     */
    public void addAll(Class<?> itemClass, List<EntityObject> itemList) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        observableLists.get(itemClass).addAll(itemList);
    }

    /**
     * Updates.
     *
     * @param itemClass the item class
     * @param item      the item
     */
    public void update(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        var list = observableLists.get(itemClass);
        list.remove(item);
        list.add(item);
    }

    /**
     * Deletes.
     *
     * @param itemClass the item class
     * @param item      the item
     */
    public void delete(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) return;
        observableLists.get(itemClass).remove(item);
    }

    /**
     * Clears the observable list.
     *
     * @param itemClass he item class
     */
    public void clearObservableList(Class<? extends EntityObject> itemClass) {
        if (observableLists.get(itemClass) != null) observableLists.get(itemClass).clear();
    }
}
