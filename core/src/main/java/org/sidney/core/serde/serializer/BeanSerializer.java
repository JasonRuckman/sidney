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

import org.sidney.core.TypeRef;
import org.sidney.core.serde.*;

import java.util.ArrayList;
import java.util.List;

public class BeanSerializer extends Serializer<Object> {
    private InstanceFactory instanceFactory;
    private List<Serializer> serializersAtThisLevel = new ArrayList<>();

    @Override
    public void consume(TypeRef typeRef, SerializerContext builder) {
        for (TypeRef.TypeFieldRef fieldRef : typeRef.getFields()) {
            Serializer fieldSerializer = builder.serializer(fieldRef, this);
            serializersAtThisLevel.add(fieldSerializer);
        }
        instanceFactory = new InstanceFactory(typeRef.getType());
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeBean(value, typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return readBean(typeReader, context);
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    private void writeBean(Object value, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(value, context)) {
            //advance into fields
            context.incrementColumnIndex();
            for (Serializer handler : serializersAtThisLevel) {
                handler.writeFromField(value, typeWriter, context);
            }
        } else {
            context.incrementColumnIndex(getNumFieldsToIncrementBy());
        }
    }

    private Object readBean(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            Object bean = instanceFactory.newInstance();
            context.incrementColumnIndex();
            for (Serializer handler : serializersAtThisLevel) {
                handler.readIntoField(bean, typeReader, context);
            }
            return bean;
        } else {
            context.incrementColumnIndex(getNumFieldsToIncrementBy());
        }
        return null;
    }
}