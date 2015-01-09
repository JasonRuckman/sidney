package org.sidney.core;

import java.util.Map;

public class NestedGenerics {
    private VeryNested<Integer, Double, Map<Integer, Double>> nested;

    public VeryNested<Integer, Double, Map<Integer, Double>> getNested() {
        return nested;
    }

    public void setNested(VeryNested<Integer, Double, Map<Integer, Double>> nested) {
        this.nested = nested;
    }
}