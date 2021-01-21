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
 *
 * @author Anna Nosek
 */
@Component
public class ActiveUser{

    /**
     * Logged-in user.
     */
    private User userEntity;

    /**
     * An active order.
     */
    private Order activeOrder;

    /**
     * Spring support for Observer Pattern.
     */
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * The service manager.
     */
    private final ServiceManager serviceManager;

    /**
     * Instantiates a new Active user.
     *
     * @param serviceManager the service manager
     */
    @Autowired
    public ActiveUser(ServiceManager serviceManager){
        this.serviceManager = serviceManager;
    }

    /**
     * Adds a user change listener.
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

    /**
     * Fills the orders.
     *
     * @param user the user
     */
    public void fillOrders(User user){
        UserService userService = (UserService) serviceManager.getService(User.class);
        OrderService orderService = (OrderService) serviceManager.getService(Order.class);

        userEntity = userService.findUserWithOrders(user);

        ArrayList<Order> ordersWithTickets = new ArrayList<>();

        userEntity.getOrders().forEach( order -> {
            ordersWithTickets.add(orderService.findOrderWithTickets(order));
        });

        userEntity.setOrders(ordersWithTickets);
    }


    /**
     * Gets the currently logged-in user.
     *
     * @return the currently logged-in user
     */
    public User getUserEntity(){
        return userEntity;
    }

    /**
     * Sets an empty order.
     */
    public void setEmptyOrder(){
        activeOrder = new Order(userEntity);
    }

    /**
     * Gets an active order.
     *
     * @return the active order.
     */
    public Order getActiveOrder(){
        return activeOrder;
    }

    /**
     * Gets the list of all the orders.
     *
     * @return the list of all the orders
     */
    public List<Order> getAllOrders(){
        return userEntity.getOrders();
    }
}
