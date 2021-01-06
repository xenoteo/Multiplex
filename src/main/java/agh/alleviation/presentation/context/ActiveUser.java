package agh.alleviation.presentation.context;

import agh.alleviation.model.Order;
import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

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

    private final UserService userService;

    /**
     * Instantiates a new Active user.
     */
    @Autowired
    public ActiveUser(UserService userService){
        this.userService = userService;
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
        propertyChangeSupport.firePropertyChange("user", userEntity, newUser);
        userEntity = newUser;
        setEmptyOrder();

    }

    public void fillOrders(User user){
        userEntity = userService.getUserWithOrders(user);
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

    public List<Order> getAllOrders(){
        return userEntity.getOrders();
    }


}
