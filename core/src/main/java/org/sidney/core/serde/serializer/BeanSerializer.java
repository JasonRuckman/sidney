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
import org.sidney.core.FieldUtils;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class BeanSerializer extends GenericSerializer {
    private Class<?> rawClass;
    private List<Serializer> fieldHandlers = new ArrayList<>();
    private InstanceFactory instanceFactory;

    public BeanSerializer(Type jdkType,
                          Field field,
                          TypeBindings parentBindings,
                          Serializers serializers, Class... generics) {
        super(jdkType, field, parentBindings, serializers, generics);

        List<Field> fields = FieldUtils.getAllFields(rawClass);
        for (int i = 0; i < fields.size(); i++) {
            Field subField = fields.get(i);
            Type type = subField.getGenericType();
            Serializer serializer = getSerializers().serializer(
                    (type == null) ? subField.getType() : type, subField, getTypeBindings()
            );

            handlers.add(serializer);
            handlers.addAll(serializer.getHandlers());

            fieldHandlers.add(serializer);
            numSubFields += serializer.numSubFields;
        }
        instanceFactory = new InstanceFactory(rawClass);
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeBean(value, typeWriter, context);
    }

    @Override
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        writeBean(getAccessor().get(parent), typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return readBean(typeReader, context);
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        getAccessor().set(parent, readBean(typeReader, context));
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    @Override
    protected void fromType(Type type) {
        rawClass = (Class) type;
    }

    @Override
    protected void fromParameterizedClass(Class<?> clazz, Class... types) {
        rawClass = Types.parameterizedType(clazz, types).getRawClass();
    }

    @Override
    protected void fromParameterizedType(ParameterizedType type) {
        rawClass = Types.type(type, getParentTypeBindings()).getRawClass();
    }

    @Override
    protected void fromTypeVariable(TypeVariable typeVariable) {
        rawClass = Types.type(typeVariable, getParentTypeBindings()).getRawClass();
    }

    private void writeBean(Object value, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(value, context)) {
            //advance into fields
            context.incrementColumnIndex();
            for (Serializer handler : fieldHandlers) {
                handler.writeFromField(value, typeWriter, context);
            }
        } else {
            context.incrementColumnIndex(numSubFields);
        }
    }

    private Object readBean(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            Object bean = instanceFactory.newInstance();
            context.incrementColumnIndex();
            for (Serializer handler : fieldHandlers) {
                handler.readIntoField(bean, typeReader, context);
            }
            return bean;
        } else {
            context.incrementColumnIndex(numSubFields);
        }
        return null;
    }

    public static class BeanSerializerFactory extends GenericSerializerFactory<BeanSerializer> {
        @Override
        public BeanSerializer newSerializer(Type type, Field field, TypeBindings typeBindings, Serializers serializers, Class... typeParameters) {
            return new BeanSerializer(type, field, typeBindings, serializers, typeParameters);
        }

        @Override
        public BeanSerializer newSerializer(Type type, Field field, TypeBindings typeBindings, Serializers serializers) {
            return new BeanSerializer(type, field, typeBindings, serializers);
        }
    }
}