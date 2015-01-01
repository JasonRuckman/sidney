package org.sidney.core.serializer;

import org.sidney.core.Container;
import org.sidney.core.Header;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {
    private static final Map<Class, Constructor> CONSTRUCTORS = new HashMap<>();

    static {
        try {
            CONSTRUCTORS.put(Boolean.class, BoolRefSerializer.class.getConstructor(Class.class, Field.class));
            CONSTRUCTORS.put(Integer.class, IntRefSerializer.class.getConstructor(Class.class, Field.class));
            CONSTRUCTORS.put(Long.class, LongRefSerializer.class.getConstructor(Class.class, Field.class));
            CONSTRUCTORS.put(Float.class, FloatRefSerializer.class.getConstructor(Class.class, Field.class));
            CONSTRUCTORS.put(DoubleRefSerializer.class, DoubleRefSerializer.class.getConstructor(Class.class, Field.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Serializer serializerWithoutField(Class<?> type, Container<Header> header) {
        return serializerInternal(type, null, header);
    }

    public static Serializer serializer(Class<?> type, Container<Header> header, Class... generics) {
        Constructor c = CONSTRUCTORS.get(type);
        if (c != null) {
            try {
                return (Serializer) c.newInstance(type, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (Collection.class.isAssignableFrom(type)) {
            return new CollectionSerializer(type, generics[0], header);
        }

        throw new IllegalStateException();
    }

    public static Serializer serializer(Field field, Container<Header> header) {
        return serializerInternal(field.getType(), field, header);
    }

    private static Serializer serializerInternal(Class<?> type, Field field, Container<Header> header) {
        Constructor c = CONSTRUCTORS.get(type);
        if (c != null) {
            try {
                return (Serializer) c.newInstance(type, field);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (PrimitiveSerializer.isPrimitive(type)) {
            return new PrimitiveSerializer(type, field);
        }

        if (type.isAssignableFrom(Collection.class)) {
            return new CollectionSerializer(type, field, header);
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

        return new BeanSerializer(type, field, header);
    }
}