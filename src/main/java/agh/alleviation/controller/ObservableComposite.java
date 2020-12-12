package agh.alleviation.controller;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservableComposite {
    private final Map<Class<?>, ObservableList<EntityObject>> observableLists;

    public ObservableComposite() {
        this.observableLists = new HashMap<>();
        this.observableLists.put(Ticket.class, FXCollections.observableArrayList());
    }

    public ObservableList<EntityObject> getList(Class<?> itemClass) {
        return observableLists.get(itemClass);
    }

    public ObservableList<EntityObject> getActiveElementsList(Class<?> itemClass) {
        return observableLists.get(itemClass).filtered(EntityObject::getIsActive);
    }

    public void addObservableList(Class<?> itemClass) {
        observableLists.put(itemClass, FXCollections.observableArrayList());
    }

    public void addItem(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        observableLists.get(itemClass).add(item);
    }

    public void addAll(Class<?> itemClass, List<EntityObject> itemList) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        observableLists.get(itemClass).addAll(itemList);
    }

    public void update(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) addObservableList(itemClass);
        var list = observableLists.get(itemClass);
        if (list.contains(item)) {
            list.remove(item);
        }
        list.add(item);
    }

    public void delete(Class<?> itemClass, EntityObject item) {
        if (observableLists.get(itemClass) == null) return;
        observableLists.get(itemClass).remove(item);
    }
}
