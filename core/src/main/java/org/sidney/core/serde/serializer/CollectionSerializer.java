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

import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionSerializer extends Serializer<Collection> {
    private Serializer contentSerializer;
    private InstanceFactoryCache cache = new InstanceFactoryCache();

    @Override
    public void postInit() {
        addToFieldCount(contentSerializer.getNumFields());
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeCollection((Collection) value, typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return readCollection(typeReader, context);
    }

    @Override
    public boolean requiresTypeColumn() {
        return true;
    }

    @Override
    protected List<Serializer> serializers() {
        List<Serializer> serializers = new ArrayList<>();
        serializers.add(contentSerializer);
        serializers.addAll(contentSerializer.getSerializers());
        return serializers;
    }

    @Override
    protected void initFromParameterizedClass(Class<?> clazz, Class... types) {
        contentSerializer = getSerializerRepository().serializer(
                types[0], null, getTypeBindings(), new Class[0]
        );
    }

    @Override
    protected void initFromParameterizedType(ParameterizedType type) {
        Type[] args = ((ParameterizedType) getJdkType()).getActualTypeArguments();
        contentSerializer = getSerializerRepository().serializer(
                args[0], null, getTypeBindings(), new Class[0]
        );
    }

    @Override
    protected void initFromTypeVariable(TypeVariable typeVariable) {
        TypeVariable variable = (TypeVariable) getJdkType();
        contentSerializer = getSerializerRepository().serializer(
                variable, null, getTypeBindings(), new Class[0]
        );
    }

    private void writeCollection(Collection collection, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarkerAndType(collection, context)) {
            context.incrementColumnIndex();
            int index = context.getColumnIndex();
            typeWriter.writeRepetitionCount(context.getColumnIndex(), collection.size(), context);
            for (Object value : collection) {
                contentSerializer.writeValue(value, typeWriter, context);
                context.setColumnIndex(index); //rewind back to the start of the component type
            }
            context.incrementColumnIndex();
        } else {
            context.incrementColumnIndex(getNumFields() + 1);
        }
    }

    private Collection readCollection(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            Collection c = (Collection) cache.newInstance(typeReader.readConcreteType(context));
            context.incrementColumnIndex();
            int count = typeReader.readRepetitionCount(context);
            int valueIndex = context.getColumnIndex();
            for (int i = 0; i < count; i++) {
                context.setColumnIndex(valueIndex);
                c.add(contentSerializer.readValue(typeReader, context));
            }
            context.incrementColumnIndex();
            return c;
        }
        context.incrementColumnIndex(getNumFields() + 1);
        return null;
    }
}