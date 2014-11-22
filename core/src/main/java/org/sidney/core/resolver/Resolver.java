package org.sidney.core.resolver;

import org.sidney.core.field.Writer;
import org.sidney.core.schema.Definition;

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

    public Class<?> getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    public abstract List<Resolver> children();

    public abstract Writer consumer();

    public abstract Definition definition();

    public String name() {
        return  (getField() == null) ? getType().getName() : getField().getName();
    }
}