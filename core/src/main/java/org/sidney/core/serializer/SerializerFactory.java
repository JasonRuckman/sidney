package org.sidney.core.serializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {
    private static final Map<Class, Constructor> CONSTRUCTORS = new HashMap<>();
    static {
        try {
            CONSTRUCTORS.put(Integer.class, IntRefSerializer.class.getConstructor(Class.class, Field.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Serializer serializer(Class<?> type) {
        return serializerInternal(type, null);
    }

    public static Serializer serializer(Class<?> type, Class... generics) {
        Constructor c = CONSTRUCTORS.get(type);
        if(c != null) {
            try {
                return (Serializer)c.newInstance(type, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(Collection.class.isAssignableFrom(type)) {
            return new CollectionSerializer(type, generics[0]);
        }

        throw new IllegalStateException();
    }

    public static Serializer serializer(Field field) {
        return serializerInternal(field.getType(), field);
    }

    private static Serializer serializerInternal(Class<?> type, Field field) {
        Constructor c = CONSTRUCTORS.get(type);
        if(c != null) {
            try {
                return (Serializer)c.newInstance(type, field);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (PrimitiveSerializer.isPrimitive(type)) {
            return new PrimitiveSerializer(type, field);
        }

        if (type.isAssignableFrom(Collection.class)) {
            return new CollectionSerializer(type, field);
        }

        /*if (type.isArray()) {
            return new ArrayResolver(type, field);
        }

        if (type.isAssignableFrom(Map.class)) {
            return new MapResolver(type, field);
        }

        if (FieldUtils.getAllFields(type).size() == 0) {
            return new EmptyClassResolver(type, field);
        }*/

        return new BeanSerializer(type, field);
    }
}