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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

//TODO: Refactor to accept registrations

/**
 * Factory that creates {@link Serializer}s
 */
public class SerializerRepository {
    protected List<SerializerEntry> entries = new ArrayList<>();
    private Registrations registrations;

    public SerializerRepository(Registrations registrations) {
        this.registrations = registrations;

        addCustomFactories();
        addPrimitiveFactories();
        addArrayFactories();

        addEntry(Collection.class, CollectionSerializer.class);
        addEntry(Map.class, MapSerializer.class);
        addEntry(Object.class, BeanSerializer.class);

    }

    private void addCustomFactories() {
        entries.addAll(registrations.getRegistrations());
    }

    private void addPrimitiveFactories() {
        Class<PrimitiveSerializer> primitiveSerializerClass = PrimitiveSerializer.class;
        Class<PrimitiveSerializer.NonNullPrimitiveSerializer> nonNullPrimitiveSerializerClass = PrimitiveSerializer.NonNullPrimitiveSerializer.class;

        addEntry(boolean.class, nonNullPrimitiveSerializerClass);
        addEntry(Boolean.class, primitiveSerializerClass);
        addEntry(byte.class, nonNullPrimitiveSerializerClass);
        addEntry(Byte.class, primitiveSerializerClass);
        addEntry(char.class, nonNullPrimitiveSerializerClass);
        addEntry(Character.class, primitiveSerializerClass);
        addEntry(short.class, nonNullPrimitiveSerializerClass);
        addEntry(Short.class, primitiveSerializerClass);
        addEntry(int.class, nonNullPrimitiveSerializerClass);
        addEntry(Integer.class, primitiveSerializerClass);
        addEntry(long.class, nonNullPrimitiveSerializerClass);
        addEntry(Long.class, primitiveSerializerClass);
        addEntry(float.class, nonNullPrimitiveSerializerClass);
        addEntry(Float.class, primitiveSerializerClass);
        addEntry(double.class, nonNullPrimitiveSerializerClass);
        addEntry(Double.class, primitiveSerializerClass);
        addEntry(Enum.class, primitiveSerializerClass);
        addEntry(String.class, primitiveSerializerClass);
        addEntry(byte[].class, primitiveSerializerClass);
    }

    private void addArrayFactories() {
        Class<ArraySerializer> arraySerializerClass = ArraySerializer.class;

        addEntry(boolean[].class, arraySerializerClass);
        addEntry(char[].class, arraySerializerClass);
        addEntry(short[].class, arraySerializerClass);
        addEntry(int[].class, arraySerializerClass);
        addEntry(long[].class, arraySerializerClass);
        addEntry(float[].class, arraySerializerClass);
        addEntry(double[].class, arraySerializerClass);
        addEntry(Object[].class, arraySerializerClass);
    }


    public Serializer serializer(Type type) {
        return serializer(type, null, null, new Class[0]);
    }

    /**
     * Create a {@link Serializer} from the given arguments
     */
    public Serializer serializer(Type type, Field field, TypeBindings typeBindings, Class[] generics) {
        JavaType javaType;
        if (generics.length == 0) {
            javaType = Types.type(type, typeBindings);
        } else {
            javaType = Types.parameterizedType((Class) type, generics);
        }
        Class<?> clazz = javaType.getRawClass();

        Serializer serializer = null;
        for (SerializerEntry entry : entries) {
            if (entry.getType() == clazz || entry.getType().isAssignableFrom(clazz)) {
                serializer = (Serializer) new InstanceFactory(entry.getSerializerType()).newInstance();
                break;
            }
        }

        if (serializer == null) {
            throw new SidneyException(String.format("Could not resolve serializer for type: %s", clazz));
        }

        if (serializer instanceof PrimitiveSerializer.NonNullPrimitiveSerializer ||
                serializer instanceof PrimitiveSerializer) {
            type = clazz;
        }

        serializer.setTypeParams(generics);
        serializer.setJdkType(type);
        serializer.setParentTypeBindings(typeBindings);
        serializer.setSerializerRepository(this);
        serializer.setParentTypeBindings(typeBindings);
        serializer.setField(field);

        serializer.resolveTypeBindings();
        serializer.preInit();
        serializer.init();
        serializer.postInit();
        serializer.finish();

        return serializer;
    }

    private void addEntry(Class type, Class<? extends Serializer> serializerType) {
        entries.add(new SerializerEntry(type, serializerType));
    }
}