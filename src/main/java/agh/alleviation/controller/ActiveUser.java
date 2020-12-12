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

    public void setUserEntity(User userEntity) {
        propertyChangeSupport.firePropertyChange("user", this.userEntity, userEntity);
        this.userEntity = userEntity;
    }

    public User getUserEntity(){
        return userEntity;
    }
}
