package org.sidney.core;

import java.util.Map;

public class NestedBeanAsTypeVariable<X, Y, Z extends Map<X, Y>> {
    private VeryGeneric<VeryNested<X, Y, Z>, VeryNested<X, Y, Z>, VeryNested<X, Y, Z>> generic;

    public VeryGeneric<VeryNested<X, Y, Z>, VeryNested<X, Y, Z>, VeryNested<X, Y, Z>> getGeneric() {
        return generic;
    }

    public void setGeneric(VeryGeneric<VeryNested<X, Y, Z>, VeryNested<X, Y, Z>, VeryNested<X, Y, Z>> generic) {
        this.generic = generic;
    }
}
