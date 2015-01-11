package org.sidney.core.serde;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class TypeUtil {
    public static TypeBindings binding(Type clazz) {
        JavaType javaType = TypeFactory.defaultInstance().constructType(clazz);
        return new TypeBindings(TypeFactory.defaultInstance(), javaType);
    }

    public static TypeBindings binding(Type clazz, TypeBindings parentBindings) {
        JavaType javaType = TypeFactory.defaultInstance().constructType(clazz, parentBindings);
        return new TypeBindings(TypeFactory.defaultInstance(), javaType);
    }

    public static TypeBindings binding(Class type, Class... generics) {
        JavaType javaType = TypeFactory.defaultInstance().constructParametricType(type, generics);
        return new TypeBindings(TypeFactory.defaultInstance(), javaType);
    }

    public static TypeBindings binding(Class type, JavaType... javaTypes) {
        JavaType javaType = TypeFactory.defaultInstance().constructParametricType(
                type, javaTypes
        );

        return new TypeBindings(TypeFactory.defaultInstance(), javaType);
    }

    public static TypeBindings binding(Field field, TypeBindings parentBindings) {
        Type t = field.getGenericType();
        if (t == null) {
            t = field.getType();
        }
        JavaType javaType = TypeFactory.defaultInstance().constructType(t, parentBindings);
        return new TypeBindings(TypeFactory.defaultInstance(), javaType);
    }

    public static JavaType type(Type type) {
        return TypeFactory.defaultInstance().constructType(type);
    }

    public static JavaType type(Type type, TypeBindings typeBindings) {
        return TypeFactory.defaultInstance().constructType(type, typeBindings);
    }

    public static JavaType parameterizedType(Class<?> type, Class<?>... paramTypes) {
        return TypeFactory.defaultInstance().constructParametricType(
                type, paramTypes
        );
    }
}
