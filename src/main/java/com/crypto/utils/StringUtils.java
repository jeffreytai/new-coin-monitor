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

    /**
     * Apply the following rules for the suffix of the url:
     * Replace any space characters, periods, or parentheses with a single dash
     * If there are two dashes consecutively, replace that with one dash
     * @param value
     * @return
     */
    public static String urlFormattedString(String value) {
        String formatted = value.replaceAll("\\.| |\\(|\\)", "-")
                .replaceAll("--", "-")
                .replaceAll("[^a-zA-Z0-9]$", "");
        return formatted;
    }
}
