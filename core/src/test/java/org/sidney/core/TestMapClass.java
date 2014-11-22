package org.sidney.core;

import java.util.HashMap;
import java.util.Map;

public class TestMapClass {
    private Map<Integer, Integer> ints = new HashMap<>();

    public Map<Integer, Integer> getInts() {
        return ints;
    }

    public void setInts(Map<Integer, Integer> ints) {
        this.ints = ints;
    }
}