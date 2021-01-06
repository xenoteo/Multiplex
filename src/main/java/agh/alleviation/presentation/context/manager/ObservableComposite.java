package agh.alleviation.presentation.context.manager;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import agh.alleviation.presentation.context.ActiveUser;
import agh.alleviation.presentation.filter.SeanceFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;

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
     * Gets list.
     *
     * @param itemClass the item class
     * @return the list
     */
    public ObservableList<EntityObject> getList(Class<?> itemClass) {
        return observableLists.get(itemClass);
    }

    /**
     * Gets active elements list.
     *
     * @param itemClass the item class
     * @return the active elements list
     */
    public ObservableList<EntityObject> getActiveElementsList(Class<?> itemClass) {
        return observableLists.get(itemClass).filtered(EntityObject::getIsActive);
    }

    /**
     * Add observable list.
     *
     * @param itemClass the item class
     */
    public void addObservableList(Class<?> itemClass) {
        observableLists.put(itemClass, FXCollections.observableArrayList());
    }

    /**
     * Add item.
     *
     * @param itemClass the item class
     * @param item      the item
     */
    public void add(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        observableLists.get(itemClass).add(item);
    }

    /**
     * Add all.
     *
     * @param itemClass the item class
     * @param itemList  the item list
     */
    public void addAll(Class<?> itemClass, List<EntityObject> itemList) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        observableLists.get(itemClass).addAll(itemList);
    }

    /**
     * Update.
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
     * Delete.
     *
     * @param itemClass the item class
     * @param item      the item
     */
    public void delete(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) return;
        observableLists.get(itemClass).remove(item);
    }

    public void clearObservableList(Class<? extends EntityObject> itemClass) {

        observableLists.get(itemClass).clear();
    }
}
