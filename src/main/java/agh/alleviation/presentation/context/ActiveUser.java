package agh.alleviation.presentation.context;

import agh.alleviation.model.Order;
import agh.alleviation.model.user.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Component responsible for maintaining the information about the currently logged-in user.
 * @author Anna Nosek
 */
@Component
public class ActiveUser{

    /**
     * Logged-in user.
     */
    private User userEntity;

    private Order activeOrder;

    /**
     * Spring support for Observer Pattern.
     */
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Instantiates a new Active user.
     */
    public ActiveUser(){


    }

    /**
     * Add user change listener.
     *
     * @param listener the listener
     */
    public void addUserChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Changes the logged-in user, notifies all observers.
     *
     * @param newUser the new user
     */
    public void setUserEntity(User newUser) {
        propertyChangeSupport.firePropertyChange("user", this.userEntity, newUser);
        userEntity = newUser;
        setEmptyOrder();
    }

    /**
     * Get the currently logged-in user.
     *
     * @return the user
     */
    public User getUserEntity(){
        return userEntity;
    }

    public void setEmptyOrder(){
        activeOrder = new Order(userEntity);
    }

    public Order getActiveOrder(){
        return activeOrder;
    }
}
