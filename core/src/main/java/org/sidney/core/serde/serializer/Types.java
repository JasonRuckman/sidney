/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core.serde.serializer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class Types {
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
