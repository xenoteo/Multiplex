package agh.alleviation.presentation;

import agh.alleviation.util.UserType;

/**
 * Enum representing navigable screens in application
 *
 * @author Kamil Krzempek, Anna Nosek
 */
public enum Screen {
    MAIN,
    USER_LIST,
    HALL_LIST,
    MOVIE_LIST,
    SEANCE_LIST;

    public int getPrivilegeLevel() {
        return switch (this) {
            case MAIN -> 0;
            case MOVIE_LIST, SEANCE_LIST -> 1;
            case USER_LIST, HALL_LIST -> 2;
        };
    }
}
