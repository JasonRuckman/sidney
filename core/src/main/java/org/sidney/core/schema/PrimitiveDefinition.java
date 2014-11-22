package org.sidney.core.schema;

public class PrimitiveDefinition extends Definition {
    private Type type;

    public PrimitiveDefinition(String name, Repetition repetition, Type type) {
        super(name, repetition);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
