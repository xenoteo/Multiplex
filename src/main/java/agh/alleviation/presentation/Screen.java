package agh.alleviation.presentation;

import agh.alleviation.util.UserType;

/**
 * Enum representing navigable screens in application
 *
 * @author Kamil Krzempek, Anna Nosek
 */
public enum Screen {
    /**
     * Main screen.
     */
    MAIN,
    /**
     * User list screen.
     */
    USER_LIST,
    /**
     * Hall list screen.
     */
    HALL_LIST,
    /**
     * Movie list screen.
     */
    MOVIE_LIST,
    /**
     * Seance list screen.
     */
    SEANCE_LIST,

    TICKET_LIST;

    /**
     * Gets privilege level.
     *
     * @return the privilege level
     */
    public int getPrivilegeLevel() {
        return switch (this) {
            case MAIN, TICKET_LIST -> 0;
            case MOVIE_LIST, SEANCE_LIST -> 1;
            case USER_LIST, HALL_LIST -> 2;
        };
    }
}
