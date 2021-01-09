package agh.alleviation.presentation;

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
    /**
     * Order list screen.
     */
    ORDER_LIST,
    /**
     * Ticket list screen.
     */
    TICKET_LIST,
    /**
     * Statistics screen.
     */
    STATISTICS;

    /**
     * Gets privilege level.
     *
     * @return the privilege level
     */
    public int getPrivilegeLevel() {
        return switch (this) {
            case MAIN, TICKET_LIST, SEANCE_LIST, ORDER_LIST, STATISTICS -> 0;
            case MOVIE_LIST -> 1;
            case USER_LIST, HALL_LIST -> 2;
        };
    }
}
