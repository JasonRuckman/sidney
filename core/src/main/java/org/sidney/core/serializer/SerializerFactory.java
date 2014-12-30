package org.sidney.core.serializer;

import java.lang.reflect.Field;

public class SerializerFactory {
    public static Serializer serializer(Class<?> type) {
        return resolver(type, null);
    }

    public static Serializer resolver(Field field) {
        return resolver(field.getType(), field);
    }

    private static Serializer resolver(Class<?> type, Field field) {
        if (PrimitiveSerializer.isPrimitive(type)) {
            return new PrimitiveSerializer(type, field);
        }

        /*if (type.isArray()) {
            return new ArrayResolver(type, field);
        }

        if (type.isAssignableFrom(Map.class)) {
            return new MapResolver(type, field);
        }

        if (type.isAssignableFrom(Collection.class)) {
            return new CollectionResolver(type, field);
        }

        if (FieldUtils.getAllFields(type).size() == 0) {
            return new EmptyClassResolver(type, field);
        }*/

        return new BeanSerializer(type, field);
    }
}