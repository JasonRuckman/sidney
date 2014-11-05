package org.sidney.core.resolver;

import org.sidney.core.field.Writer;

import java.lang.reflect.Field;
import java.util.List;

public abstract class Resolver {
    private final Class<?> type;
    private final Field field;
    private final List<Resolver> childResolvers;

    public Resolver(Class<?> type, Field field) {
        this.type = type;
        this.field = field;
        this.childResolvers = children();
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

    protected abstract List<Resolver> children();
    public abstract Writer consumer();
}