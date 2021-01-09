package agh.alleviation.util;

public enum Rating {
    NONE, POSITIVE, NEGATIVE;

    private static Rating[] allValues = values();
    public static Rating fromOrdinal(int n) {return allValues[n];}
}
