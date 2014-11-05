package org.sidney.core.field;

import java.lang.reflect.Field;

public abstract class Writer {
    private final Field field;
    private final FieldAccessor accessor;

    public Writer(Field field) {
        this.field = field;
        this.accessor = new UnsafeFieldAccessor(field);
    }

    public final void startField(RecordWriter writer) {
        writer.startField();
    }

    public final void endField(RecordWriter writer) {
        writer.endField();
    }

    public abstract void writeField(Object parent, RecordWriter recordWriter);
}