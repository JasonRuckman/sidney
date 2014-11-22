package org.sidney.core.schema;

import org.sidney.core.schema.Repetition;

public class Definition {
    private String name;
    private Repetition repetition;

    public Definition(String name, Repetition repetition) {
        this.name = name;
        this.repetition = repetition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }
}