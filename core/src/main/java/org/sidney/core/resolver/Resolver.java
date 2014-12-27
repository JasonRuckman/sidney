package org.sidney.core.resolver;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.schema.Type;

import java.lang.reflect.Field;
import java.util.List;

public abstract class Resolver {
    private final Class<?> type;
    private final Field field;

    public Resolver(Class<?> type, Field field) {
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

    public abstract List<Resolver> children();

    public abstract void writeRecord(MessageConsumer consumer, Object value, int index);

    public abstract void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor);

    public String name() {
        return (getField() == null) ? getJdkType().getName() : getField().getName();
    }
}