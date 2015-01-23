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

import org.sidney.core.Fields;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BeanSerializer extends Serializer<Object> {
    private InstanceFactory instanceFactory;
    private List<Serializer> serializersAtThisLevel;

    @Override
    public void postInit() {
        instanceFactory = new InstanceFactory(getRawClass());
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

    @Override
    protected List<Serializer> serializers() {
        List<Serializer> serializers = new ArrayList<>();
        serializersAtThisLevel = new ArrayList<>();
        List<Field> fields = Fields.getAllFields(getRawClass());
        for (Field subField : fields) {
            Type type = subField.getGenericType();
            Serializer serializer = getSerializerRepository().serializer(
                    (type == null) ? subField.getType() : type, subField, getTypeBindings(), new Class[0]
            );

            serializers.add(serializer);
            serializers.addAll(serializer.getSerializers());

            serializersAtThisLevel.add(serializer);
            addToFieldCount(serializer.getNumFields());
        }
        return serializers;
    }

    private void writeBean(Object value, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(value, context)) {
            //advance into fields
            context.incrementColumnIndex();
            for (Serializer handler : serializersAtThisLevel) {
                handler.writeFromField(value, typeWriter, context);
            }
        } else {
            context.incrementColumnIndex(getNumFields());
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
            context.incrementColumnIndex(getNumFields());
        }
        return null;
    }
}