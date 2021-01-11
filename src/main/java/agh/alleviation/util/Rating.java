package agh.alleviation.util;

/**
 * Rating of movie. Assigned in ticket. Max one rating per ticket.
 *
 * @author Anna Nosek
 */
public enum Rating {
    /**
     * None rating.
     */
    NONE,
    /**
     * Positive rating.
     */
    POSITIVE,
    /**
     * Negative rating.
     */
    NEGATIVE;

    private static final Rating[] allValues = values();

    /**
     * From ordinal rating.
     *
     * @param n the n
     * @return the rating
     */
    public static Rating fromOrdinal(int n) {return allValues[n];}
}
