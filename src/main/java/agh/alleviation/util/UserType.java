package agh.alleviation.util;

/**
 * This enum represents different types of user. Helps creating user-related views and services.
 * For each type of user, privilege level is assigned. User has access to screens with equal or lower privilege level.
 *
 * @author Anna Nosek
 * @see agh.alleviation.model.user.User
 * @see agh.alleviation.model.user.Customer
 * @see agh.alleviation.model.user.Admin
 * @see agh.alleviation.model.user.Worker
 * @see agh.alleviation.presentation.Screen
 */
public enum UserType {
    /**
     * Admin user type.
     */
    ADMIN("Admin"),
    /**
     * Worker user type.
     */
    WORKER("Worker"),
    /**
     * Customer user type.
     */
    CUSTOMER("Customer");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getPrivilegeLevel() {
        return switch (this) {
            case CUSTOMER -> 0;
            case WORKER -> 1;
            case ADMIN -> 2;
        };
    }
}
