package org.sidney.core.schema;

import java.util.ArrayList;
import java.util.List;

public class GroupDefinition extends Definition {
    private List<Definition> children = new ArrayList<>();

    public GroupDefinition(String name, Repetition repetition) {
        super(name, repetition);
    }

    public List<Definition> getChildren() {
        return children;
    }
}