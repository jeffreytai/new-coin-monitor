package com.crypto.utils;

public class StringUtils {

    /**
     * Alias for an empty string
     */
    public static final String EMPTY_STRING = "";

    /**
     * Returns true if 2 strings are equal ignoring case sensitivity
     * @param val1
     * @param val2
     * @return
     */
    public static boolean caseInsensitiveEquals(String val1, String val2) {
        return val1.toLowerCase().equals(val2.toLowerCase());
    }
}
