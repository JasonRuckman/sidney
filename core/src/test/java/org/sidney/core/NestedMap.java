package org.sidney.core;

import java.util.Map;

public class NestedMap<X, Y> {
    private Map<X, Y> map;

    public Map<X, Y> getMap() {
        return map;
    }

    public void setMap(Map<X, Y> map) {
        this.map = map;
    }
}