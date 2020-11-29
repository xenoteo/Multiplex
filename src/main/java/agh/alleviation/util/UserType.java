package agh.alleviation.util;

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
