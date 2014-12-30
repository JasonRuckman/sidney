package org.sidney.core.serializer;

import org.sidney.core.writer.ColumnWriter;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.reader.ColumnReader;
import org.sidney.core.schema.Type;

import java.lang.reflect.Field;
import java.util.List;

public abstract class Serializer {
    private final Class type;
    private final Field field;

    public Serializer(Class type, Field field) {
        this.type = type;
        this.field = field;
    }

    @Override
    public String toString() {
        return "Resolver{" +
                "type=" + type +
                ", field=" + field +
                '}';
    }

    public Class<?> getJdkType() {
        return type;
    }

    public Type getType() {
        return Type.GROUP;
    }

    public Field getField() {
        return field;
    }

    public abstract List<Serializer> children();

    public abstract int writeRecord(ColumnWriter consumer, Object value, int index);

    public abstract int writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor);

    public abstract Object nextRecord(ColumnReader columnReader, int index);

    public abstract int readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor);

    public String name() {
        return (getField() == null) ? getJdkType().getName() : getField().getName();
    }

    public abstract int numFields();
}