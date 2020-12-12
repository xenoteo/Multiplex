package agh.alleviation.controller;

import agh.alleviation.model.user.User;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Component
public class ActiveUser{

    private User userEntity;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ActiveUser(){
    }

    public void addUserChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void setUserEntity(User newUser) {
        propertyChangeSupport.firePropertyChange("user", this.userEntity, newUser);
        this.userEntity = newUser;
    }

    public User getUserEntity(){
        return userEntity;
    }
}
