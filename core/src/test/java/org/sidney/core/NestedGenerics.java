package org.sidney.core;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.Map;

public class NestedGenerics {
    private Map<VeryGeneric<Integer, Float, Double>, VeryGeneric<byte[], String, Bool>> map = new HashMap<>();

    public Map<VeryGeneric<Integer, Float, Double>, VeryGeneric<byte[], String, Bool>> getMap() {
        return map;
    }

    public void setMap(Map<VeryGeneric<Integer, Float, Double>, VeryGeneric<byte[], String, Bool>> map) {
        this.map = map;
    }
}
