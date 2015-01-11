package org.sidney.core.serde;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypeHandlerFactory {
    private static final Set<Class> PRIMITIVES = new HashSet<>();
    private static final TypeHandlerFactory instance = new TypeHandlerFactory();

    private TypeHandlerFactory() {

    }

    static {
        PRIMITIVES.add(boolean.class);
        PRIMITIVES.add(Boolean.class);
        PRIMITIVES.add(byte.class);
        PRIMITIVES.add(Byte.class);
        PRIMITIVES.add(char.class);
        PRIMITIVES.add(Character.class);
        PRIMITIVES.add(short.class);
        PRIMITIVES.add(Short.class);
        PRIMITIVES.add(int.class);
        PRIMITIVES.add(Integer.class);
        PRIMITIVES.add(long.class);
        PRIMITIVES.add(Long.class);
        PRIMITIVES.add(float.class);
        PRIMITIVES.add(Float.class);
        PRIMITIVES.add(double.class);
        PRIMITIVES.add(Double.class);
        PRIMITIVES.add(String.class);
        PRIMITIVES.add(byte[].class);
    }

    public static TypeHandlerFactory instance() {
        return instance;
    }

    public TypeHandler handler(Type type, Field field, TypeBindings typeBindings, Class... generics) {
        JavaType javaType;
        if(generics.length == 0) {
            javaType = TypeUtil.type(type, typeBindings);
        } else {
            javaType = TypeUtil.parameterizedType((Class)type, generics);
        }
        Class<?> clazz = javaType.getRawClass();

        if (PRIMITIVES.contains(clazz) || clazz.isEnum()) {
            return new PrimitiveTypeHandler(clazz, field, typeBindings, this);
        }

        if (clazz.isArray()) {
            return new ArrayTypeHandler(type, field, typeBindings, this, generics);
        }

        if (Map.class.isAssignableFrom(clazz)) {
            return new MapTypeHandler<>(type, field, typeBindings, this, generics);
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            return new CollectionTypeHandler<>(type, field, typeBindings, this, generics);
        }

        return new BeanTypeHandler(type, field, typeBindings, this, generics);
    }
}