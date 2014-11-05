package org.sidney.core.resolver;

import org.sidney.core.field.FieldUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ResolverFactory {
    public static Resolver resolver(Class<?> type) {
        return resolver(type, null);
    }

    public static Resolver resolver(Field field) {
        return resolver(field.getType(), field);
    }

    private static Resolver resolver(Class<?> type, Field field) {
        if(FieldUtils.isConsideredPrimitive(type)) {
            return new PrimitiveResolver(type, field);
        }

        if(type.isArray()) {
            return new ArrayResolver(type, field);
        }

        if(type.isAssignableFrom(Map.class)) {
            return new MapResolver(type, field);
        }

        if(type.isAssignableFrom(Collection.class)) {
            return new CollectionResolver(type, field);
        }

        if(FieldUtils.getAllFields(type).size() == 0) {
            return new EmptyClassResolver(type, field);
        }

        return new FieldResolver(type, field);
    }
}