package org.sidney.core.schema;

public enum Repetition {
    REQUIRED(0),
    OPTIONAL(1),
    REPEATED(2);

    private final int value;

    public int getValue() {
        return value;
    }

    Repetition(int value) {
        this.value = value;
    }
}