package agh.alleviation.service;

import agh.alleviation.model.EntityObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public abstract class EntityObjectService<E extends EntityObject, R extends CrudRepository<E, Integer>> {

    protected R repository;

    public void delete(E e){
        e.delete();
        repository.save(e);
    }

    public List<E> getAll(){
        return (List<E>) repository.findAll();
    }

    public List<E> getAllActive(){
        var allItems = getAll();
        return allItems.stream().filter(EntityObject::getIsActive).collect(Collectors.toList());
    }


}
