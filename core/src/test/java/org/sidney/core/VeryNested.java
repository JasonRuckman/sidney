package org.sidney.core;

import java.util.Map;

public class VeryNested<X, Y, Z extends Map<X, Y>> {
    private VeryGeneric<X, Y, Z> generic;
    private Z map;

    public Z getMap() {
        return map;
    }

    public void setMap(Z map) {
        this.map = map;
    }

    public VeryGeneric<X, Y, Z> getGeneric() {
        return generic;
    }

    public void setGeneric(VeryGeneric<X, Y, Z> generic) {
        this.generic = generic;
    }
}