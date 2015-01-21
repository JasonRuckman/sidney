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

import com.fasterxml.jackson.databind.type.TypeBindings;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.Arrays;

public class MapSerializer extends GenericSerializer<Map> {
    private Serializer keySerializer;
    private Serializer valueSerializer;
    private InstanceFactoryCache cache = new InstanceFactoryCache();

    public MapSerializer(Type jdkType,
                         Field field,
                         TypeBindings parentTypeBindings,
                         SerializerRepository serializerRepository, Class... generics) {
        super(jdkType, field, parentTypeBindings, serializerRepository, generics);

        numSubFields += keySerializer.numSubFields;
        numSubFields += valueSerializer.numSubFields;
    }

    @Override
    protected List<Serializer> fromParameterizedClass(Class<?> clazz, Class[] typeParams) {
        keySerializer = getSerializerRepository().serializer(typeParams[0], null, getTypeBindings(), new Class[0]);
        valueSerializer = getSerializerRepository().serializer(typeParams[1], null, getTypeBindings(), new Class[0]);
        return createSubSerializers();
    }

    @Override
    protected List<Serializer> fromParameterizedType(ParameterizedType type) {
        Type[] args = ((ParameterizedType) getJdkType()).getActualTypeArguments();
        keySerializer = getSerializerRepository().serializer(args[0], null, getParentTypeBindings(), new Class[0]);
        valueSerializer = getSerializerRepository().serializer(args[1], null, getParentTypeBindings(), new Class[0]);
        return createSubSerializers();
    }

    @Override
    protected List<Serializer> fromTypeVariable(TypeVariable typeVariable) {
        keySerializer = getSerializerRepository().serializer(
                typeVariable.getBounds()[0], null, getParentTypeBindings(), new Class[0]);
        valueSerializer = getSerializerRepository().serializer(
                typeVariable.getBounds()[1], null, getParentTypeBindings(), new Class[0]);
        return createSubSerializers();
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeMap((Map) value, typeWriter, context);
    }

    @Override
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        Map map = (Map) getAccessor().get(parent);
        writeMap(map, typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return readMap(typeReader, context);
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        getAccessor().set(parent, readMap(typeReader, context));
    }

    @Override
    public boolean requiresTypeColumn() {
        return true;
    }

    protected List<Serializer> createSubSerializers() {
        List<Serializer> serializers = new ArrayList<>();
        serializers.add(keySerializer);
        serializers.addAll(keySerializer.getSerializers());
        serializers.add(valueSerializer);
        serializers.addAll(valueSerializer.getSerializers());
        return serializers;
    }

    private void writeMap(Map map, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarkerAndType(map, context)) {
            context.getColumnWriter().writeRepetitionCount(context.getColumnIndex(), map.size());
            //bump index forward to key
            context.incrementColumnIndex();
            int valueIndex = context.getColumnIndex();
            for (Object e : map.entrySet()) {
                context.setColumnIndex(valueIndex); //for each entry, make sure we are starting at the root
                Map.Entry entry = (Map.Entry) e;
                keySerializer.writeValue(entry.getKey(), typeWriter, context);
                valueSerializer.writeValue(entry.getValue(), typeWriter, context);
            }
        } else {
            context.incrementColumnIndex(numSubFields + 1);
        }
    }

    private Map readMap(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            Map map = (Map) cache.newInstance(typeReader.readConcreteType(context));
            int size = typeReader.readRepetitionCount(context);
            context.incrementColumnIndex();
            int idx = context.getColumnIndex();
            for (int i = 0; i < size; i++) {
                context.setColumnIndex(idx);
                map.put(keySerializer.readValue(typeReader, context), valueSerializer.readValue(typeReader, context));
            }
            return map;
        }
        context.incrementColumnIndex(numSubFields + 1);
        return null;
    }

    public static class MapSerializerFactory extends GenericSerializerFactory {
        @Override
        public MapSerializer newSerializer(Type type, Field field, TypeBindings typeBindings, SerializerRepository serializerRepository, Class... typeParameters) {
            return new MapSerializer(
                    type, field, typeBindings, serializerRepository, typeParameters
            );
        }

        @Override
        public MapSerializer newSerializer(Type type, Field field, TypeBindings typeBindings, SerializerRepository serializerRepository) {
            return new MapSerializer(type, field, typeBindings, serializerRepository);
        }
    }
}