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
package org.sidney.core.serde;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import org.sidney.core.Registrations;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypeHandlerFactory {
    private static final Set<Class> PRIMITIVES = new HashSet<>();
    private Registrations registrations;

    public TypeHandlerFactory(Registrations registrations) {
        this.registrations = registrations;
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

    public TypeHandler handler(Type type, Field field, TypeBindings typeBindings, Class... generics) {
        JavaType javaType;
        if (generics.length == 0) {
            javaType = TypeUtil.type(type, typeBindings);
        } else {
            javaType = TypeUtil.parameterizedType((Class) type, generics);
        }
        Class<?> clazz = javaType.getRawClass();

        if (PRIMITIVES.contains(clazz) || clazz.isEnum()) {
            if (clazz.isPrimitive()) {
                return new PrimitiveTypeHandler.NonNullPrimitiveTypeHandler(clazz, field, typeBindings, this);
            }
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