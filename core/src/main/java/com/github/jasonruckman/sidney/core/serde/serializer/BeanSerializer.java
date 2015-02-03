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
package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Default serializer for non-primitive types that aren't consumed by another type
 */
public class BeanSerializer extends Serializer<Object> {
    private InstanceFactory instanceFactory;
    private List<Serializer> serializersAtThisLevel = new ArrayList<>();

    @Override
    public void consume(TypeRef typeRef, SerializerContext context) {
        for (TypeRef.TypeFieldRef fieldRef : typeRef.getFields()) {
            Serializer fieldSerializer = context.serializer(fieldRef, this);
            serializersAtThisLevel.add(fieldSerializer);
        }
        instanceFactory = new InstanceFactory(typeRef.getType());
    }

    @Override
    public void writeValue(Object value, WriteContext context) {
        writeBean(value, context);
    }

    @Override
    public Object readValue(ReadContext context) {
        return readBean(context);
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    private void writeBean(Object value, WriteContext context) {
        if (context.writeNullMarker(value)) {
            //advance into fields
            context.incrementColumnIndex();
            for (Serializer handler : serializersAtThisLevel) {
                handler.writeFromField(value, context);
            }
        } else {
            context.incrementColumnIndex(getNumFieldsToIncrementBy());
        }
    }

    private Object readBean(ReadContext context) {
        if (context.readNullMarker()) {
            Object bean = instanceFactory.newInstance();
            context.incrementColumnIndex();
            for (Serializer handler : serializersAtThisLevel) {
                handler.readIntoField(bean, context);
            }
            return bean;
        } else {
            context.incrementColumnIndex(getNumFieldsToIncrementBy());
        }
        return null;
    }
}