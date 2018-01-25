package com.crypto.utils;

import com.google.gson.JsonArray;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public static Stream<?> arrayToStream(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false);
    }
}
