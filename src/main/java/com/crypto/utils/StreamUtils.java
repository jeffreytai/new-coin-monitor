package com.crypto.utils;

import com.google.gson.JsonArray;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    /**
     * Take in a JsonArray and return it as a stream object.
     * This allows the array to have lambda functions performed on it
     * @param array
     * @return
     */
    public static Stream<?> arrayToStream(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false);
    }
}
