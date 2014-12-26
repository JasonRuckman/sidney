package org.sidney.core.resolver;

import org.sidney.core.schema.Definition;
import org.sidney.core.schema.Type;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Resolver {
    private static final Map<Class<?>, Type> TYPES = new HashMap<>();
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
        return TYPES.get(getJdkType());
    }

    public Field getField() {
        return field;
    }

    public abstract List<Resolver> children();

    public abstract Definition definition();

    public String name() {
        return (getField() == null) ? getJdkType().getName() : getField().getName();
    }
}