package org.sidney.core;

import java.util.Map;

public class NestedBean {
    private NestedBeanAsTypeVariable<Integer, Double, Map<Integer, Double>> variable;

    public NestedBeanAsTypeVariable<Integer, Double, Map<Integer, Double>> getVariable() {
        return variable;
    }

    public void setVariable(NestedBeanAsTypeVariable<Integer, Double, Map<Integer, Double>> variable) {
        this.variable = variable;
    }
}
