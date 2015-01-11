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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NestedMap nestedMap = (NestedMap) o;

        if (map != null ? !map.equals(nestedMap.map) : nestedMap.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return map != null ? map.hashCode() : 0;
    }
}