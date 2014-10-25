package org.sidney.core.resolver;

import org.sidney.core.field.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Resolver<T> {
    private final Class<T> type;
    private final Field field;
    private final List<Resolver<?>> childResolvers = new ArrayList<>();
    public Resolver(Class<T> type) {
        this(type, null);
    }

    public Resolver(Class<T> type, Field field) {
        this.type = type;
        this.field = field;

        List<Field> childFields = FieldUtils.getAllFields(type);

        if(childFields.size() > 0) {
            for(Field f : childFields) {
                childResolvers.add(new Resolver<>(f.getType(), f));
            }
        }
    }

    @Override
    public String toString() {
        return "Resolver{" +
            "type=" + type +
            ", field=" + field +
            '}';
    }

    public Class<T> getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    private boolean isPrimitive() {
        return FieldUtils.isConsideredPrimitive(type);
    }

    public void findLeaves(List<Resolver<?>> resolvers) {
        for(Resolver<?> resolver : childResolvers) {
            resolver.findLeaves(resolvers);
        }

        if(isPrimitive()) {
            resolvers.add(this);
        }
    }
}