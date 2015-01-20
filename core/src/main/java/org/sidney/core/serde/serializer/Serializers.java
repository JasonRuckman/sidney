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
import org.sidney.core.Registrations;
import org.sidney.core.SidneyException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

//TODO: Refactor to accept registrations

/**
 * Factory that creates {@link Serializer}s
 */
public class Serializers {
    private Registrations registrations;
    private List<SerializerFactoryEntry> entries = new ArrayList<>();

    public Serializers(Registrations registrations) {
        this.registrations = registrations;

        addCustomFactories();
        addPrimitiveFactories();
        addArrayFactories();

        addEntry(Collection.class, new CollectionSerializer.CollectionSerializerFactory());
        addEntry(Map.class, new MapSerializer.MapSerializerFactory());
        addEntry(Object.class, new BeanSerializer.BeanSerializerFactory());

    }

    private void addCustomFactories() {
        entries.addAll(registrations.getRegistrations());
    }

    private void addPrimitiveFactories() {
        PrimitiveSerializer.PrimitiveSerializerFactory primitiveSerializerFactory = new PrimitiveSerializer.PrimitiveSerializerFactory();
        PrimitiveSerializer.NonNullPrimitiveSerializerFactory nonNullPrimitiveSerializerFactory = new PrimitiveSerializer.NonNullPrimitiveSerializerFactory();

        addEntry(boolean.class, nonNullPrimitiveSerializerFactory);
        addEntry(Boolean.class, primitiveSerializerFactory);
        addEntry(byte.class, nonNullPrimitiveSerializerFactory);
        addEntry(Byte.class, primitiveSerializerFactory);
        addEntry(char.class, nonNullPrimitiveSerializerFactory);
        addEntry(Character.class, primitiveSerializerFactory);
        addEntry(short.class, nonNullPrimitiveSerializerFactory);
        addEntry(Short.class, primitiveSerializerFactory);
        addEntry(int.class, nonNullPrimitiveSerializerFactory);
        addEntry(Integer.class, primitiveSerializerFactory);
        addEntry(long.class, nonNullPrimitiveSerializerFactory);
        addEntry(Long.class, primitiveSerializerFactory);
        addEntry(float.class, nonNullPrimitiveSerializerFactory);
        addEntry(Float.class, primitiveSerializerFactory);
        addEntry(double.class, nonNullPrimitiveSerializerFactory);
        addEntry(Double.class, primitiveSerializerFactory);
        addEntry(Enum.class, primitiveSerializerFactory);
        addEntry(String.class, primitiveSerializerFactory);
        addEntry(byte[].class, primitiveSerializerFactory);
    }

    private void addArrayFactories() {
        ArraySerializer.ArraySerializerFactory arraySerializerFactory = new ArraySerializer.ArraySerializerFactory();

        addEntry(boolean[].class, arraySerializerFactory);
        addEntry(char[].class, arraySerializerFactory);
        addEntry(short[].class, arraySerializerFactory);
        addEntry(int[].class, arraySerializerFactory);
        addEntry(long[].class, arraySerializerFactory);
        addEntry(float[].class, arraySerializerFactory);
        addEntry(double[].class, arraySerializerFactory);
        addEntry(Object[].class, arraySerializerFactory);
    }

    /**
     * Create a {@link Serializer} from the given arguments
     */
    public Serializer serializer(Type type, Field field, TypeBindings typeBindings, Class... generics) {
        JavaType javaType;
        if (generics.length == 0) {
            javaType = Types.type(type, typeBindings);
        } else {
            javaType = Types.parameterizedType((Class) type, generics);
        }
        Class<?> clazz = javaType.getRawClass();

        SerializerFactory factory = null;
        for(SerializerFactoryEntry entry : entries) {
            if(entry.getType() == clazz || entry.getType().isAssignableFrom(clazz)) {
                factory = entry.getSerializerFactory();
                break;
            }
        }

        if(factory instanceof PrimitiveSerializer.NonNullPrimitiveSerializerFactory ||
                factory instanceof PrimitiveSerializer.PrimitiveSerializerFactory) {
            type = clazz;
        }

        if(generics.length > 0) {
            return ((GenericSerializerFactory) factory).newSerializer(
                    type, field, typeBindings, this, generics
            );
        } else {
            return factory.newSerializer(type, field, typeBindings, this);
        }
    }

    private void addEntry(Class type, SerializerFactory serializerFactory) {
        entries.add(new SerializerFactoryEntry(type, serializerFactory));
    }
}