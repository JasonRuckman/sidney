package org.sidney.core.field;

public abstract class Reader {
    public abstract void startField();

    public abstract void writeInto(Object parent);

    public abstract void endField();
}
