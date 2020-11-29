package agh.alleviation.util;

/**
 * @author Anna Nosek
 * This enum represents different types of user.
 * Helps creating user-related views and services.
 * @see agh.alleviation.model.user.User
 * @see agh.alleviation.model.user.Customer
 * @see agh.alleviation.model.user.Admin
 * @see agh.alleviation.model.user.Worker
 */

public enum UserType {
    ADMIN("Admin"), WORKER("Worker"), CUSTOMER("Customer");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
