package org.sidney.core;

import java.util.ArrayList;
import java.util.List;

public class NestedCollection {
    private List<VeryGeneric<Integer, Double, String>> list = new ArrayList<>();

    public List<VeryGeneric<Integer, Double, String>> getList() {
        return list;
    }

    public void setList(List<VeryGeneric<Integer, Double, String>> list) {
        this.list = list;
    }
}