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
 * @author Anna Nosek
 */
@Service
@Transactional
public abstract class EntityObjectService<E extends EntityObject, R extends CrudRepository<E, Integer>> {
    protected R repository;

    public List<E> getAll() {
        return (List<E>) repository.findAll();
    }

    public List<EntityObject> getAllActive() {
        var allItems = getAll();
        List<EntityObject> filtered = allItems.stream().filter(EntityObject::getIsActive).collect(Collectors.toList());
        return filtered;
    }

    public void add(EntityObject e) {
        repository.save((E) e);
    }

    public void update(EntityObject item) {
        repository.save((E) item);
    }

    public List<EntityObject> delete(EntityObject e) {
        List<EntityObject> deletedObjectList = e.delete();
        repository.save((E) e);
        return deletedObjectList;
    }
}
