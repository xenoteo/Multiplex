package agh.alleviation.presentation.context;

import agh.alleviation.model.Order;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.context.manager.ServiceManager;
import agh.alleviation.service.OrderService;
import agh.alleviation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
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

    private final ServiceManager serviceManager;

    /**
     * Instantiates a new Active user.
     */
    @Autowired
    public ActiveUser(ServiceManager serviceManager){
        this.serviceManager = serviceManager;
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

        UserService userService = (UserService) serviceManager.getService(User.class);
        OrderService orderService = (OrderService) serviceManager.getService(Order.class);

        userEntity = userService.getUserWithOrders(user);

        ArrayList<Order> ordersWithTickets = new ArrayList<>();

        userEntity.getOrders().forEach( order -> {
            ordersWithTickets.add(orderService.getOrderWithTickets(order));
        });

        userEntity.setOrders(ordersWithTickets);
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
