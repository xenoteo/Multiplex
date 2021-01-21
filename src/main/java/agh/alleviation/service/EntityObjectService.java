package agh.alleviation.service;

import agh.alleviation.model.EntityObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for all services. Provides default methods implementations for data adding, updating, deleting and retrieving.
 * This class takes two generics - one for object class and second for repository class.
 *
 * @param <E> subclass of EntityObject
 * @param <R> repository for the entity of type E
 * @author Anna Nosek
 */
@Service
@Transactional
public abstract class EntityObjectService<E extends EntityObject, R extends CrudRepository<E, Integer>> {
    /**
     * The Repository.
     */
    protected R repository;

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<EntityObject> getAll() {
        return (List<EntityObject>) repository.findAll();
    }

    /**
     * Gets all active.
     *
     * @return the all active
     */
    public List<EntityObject> getAllActive() {
        var allItems = getAll();
        List<EntityObject> filtered = allItems.stream().filter(EntityObject::getIsActive).collect(Collectors.toList());
        return filtered;
    }

    /**
     * Adds a new object.
     *
     * @param e the object to be persisted
     */
    public void add(EntityObject e) {
        repository.save((E) e);
    }

    /**
     * Updates an object.
     *
     * @param item the item
     * @return the list
     */
    public List<EntityObject> update(EntityObject item) {
        List<EntityObject> updatedObjectList = item.update();
        repository.save((E) item);
        return updatedObjectList;
    }

    /**
     * Deletes a list.
     *
     * @param item the item
     * @return the list
     */
    public List<EntityObject> delete(EntityObject item) {
        List<EntityObject> deletedObjectList = item.delete();
        repository.save((E) item);
        return deletedObjectList;
    }
}
