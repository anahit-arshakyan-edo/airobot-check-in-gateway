package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import java.util.HashMap;
import java.util.Map;

public class EnumMap<S, T> {
    private final Map<S, T> map =
            new HashMap<>();

    public EnumMap<S, T> put(S s, T t) {
        map.put(s, t);
        return this;
    }

    public T getOrThrow(S s) {
        T result = map.get(s);
        if (result == null) {
            throw new IllegalArgumentException("Invalid value: " + s);
        }
        return result;
    }
}
