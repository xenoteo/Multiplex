package agh.alleviation.util;

/**
 * Rating of movie. Assigned in ticket. Max one rating per ticket.
 *
 * @author Anna Nosek
 */
public enum Rating {
    NONE,
    POSITIVE,
    NEGATIVE;

    private static final Rating[] allValues = values();
    public static Rating fromOrdinal(int n) {return allValues[n];}
}
