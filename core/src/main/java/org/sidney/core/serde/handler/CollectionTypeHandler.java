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
package org.sidney.core.serde.handler;

import com.fasterxml.jackson.databind.type.TypeBindings;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

public class CollectionTypeHandler extends GenericTypeHandler {
    private TypeHandler contentTypeHandler;
    private InstanceFactoryCache cache = new InstanceFactoryCache();

    public CollectionTypeHandler(Type jdkType,
                                 Field field,
                                 TypeBindings parentTypeBindings,
                                 TypeHandlerFactory typeHandlerFactory, Class... generics) {
        super(jdkType, field, parentTypeBindings, typeHandlerFactory, generics);

        handlers.add(contentTypeHandler);
        handlers.addAll(contentTypeHandler.getHandlers());

        numSubFields += contentTypeHandler.numSubFields;
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeCollection((Collection) value, typeWriter, context);
    }

    @Override
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        writeCollection((Collection) getAccessor().get(parent), typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return readCollection(typeReader, context);
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        getAccessor().set(parent, readCollection(typeReader, context));
    }

    @Override
    public boolean requiresTypeColumn() {
        return true;
    }

    @Override
    protected void fromParameterizedClass(Class<?> clazz, Class... types) {
        contentTypeHandler = getTypeHandlerFactory().handler(
                types[0], null, getTypeBindings()
        );
    }

    @Override
    protected void fromParameterizedType(ParameterizedType type) {
        Type[] args = ((ParameterizedType) getJdkType()).getActualTypeArguments();
        contentTypeHandler = getTypeHandlerFactory().handler(
                args[0], null, getTypeBindings()
        );
    }

    @Override
    protected void fromTypeVariable(TypeVariable typeVariable) {
        TypeVariable variable = (TypeVariable) getJdkType();
        contentTypeHandler = getTypeHandlerFactory().handler(
                variable, null, getTypeBindings()
        );
    }

    private void writeCollection(Collection collection, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarkerAndType(collection, context)) {
            context.incrementColumnIndex();
            int index = context.getColumnIndex();
            typeWriter.writeRepetitionCount(context.getColumnIndex(), collection.size(), context);
            for (Object value : collection) {
                contentTypeHandler.writeValue(value, typeWriter, context);
                context.setColumnIndex(index); //rewind back to the start of the component type
            }
            context.incrementColumnIndex();
        } else {
            context.incrementColumnIndex(numSubFields);
        }
    }

    private Object readCollection(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            Collection c = (Collection) cache.newInstance(typeReader.readConcreteType(context));
            context.incrementColumnIndex();
            int count = typeReader.readRepetitionCount(context);
            int valueIndex = context.getColumnIndex();
            for (int i = 0; i < count; i++) {
                context.setColumnIndex(valueIndex);
                c.add(contentTypeHandler.readValue(typeReader, context));
            }
            context.incrementColumnIndex();
            return c;
        }
        context.incrementColumnIndex(numSubFields);
        return null;
    }
}