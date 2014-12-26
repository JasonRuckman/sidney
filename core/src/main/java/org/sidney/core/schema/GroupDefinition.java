package org.sidney.core.schema;

import java.util.ArrayList;
import java.util.List;

public class GroupDefinition extends Definition {
    private List<Definition> children = new ArrayList<>();

    public GroupDefinition(String name, Repetition repetition) {
        super(name, repetition);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    public List<Definition> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "GroupDefinition{" + "name=" + getName() + " , " +
                "children=" + children +
                '}';
    }
}