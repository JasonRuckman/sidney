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

import com.fasterxml.jackson.databind.type.TypeBindings;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.field.UnsafeFieldAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class TypeHandler {
    protected List<TypeHandler> handlers = new ArrayList<>();
    protected int numSubFields = 1;
    protected Class[] generics;
    private Type jdkType;
    private Field field;
    private TypeBindings parentTypeBindings;
    private FieldAccessor accessor;
    private TypeHandlerFactory typeHandlerFactory;
    private TypeBindings typeBindings;

    public TypeHandler(Type jdkType,
                       Field field,
                       TypeBindings parentTypeBindings,
                       TypeHandlerFactory typeHandlerFactory, Class... generics) {
        this.jdkType = jdkType;
        this.field = field;
        this.parentTypeBindings = parentTypeBindings;
        this.accessor = (field != null) ? new UnsafeFieldAccessor(field) : null;
        this.typeHandlerFactory = typeHandlerFactory;
        this.generics = generics;

        resolveTypes();
    }

    public TypeBindings getParentTypeBindings() {
        return parentTypeBindings;
    }

    protected void resolveTypes() {
        if (field != null) {
            typeBindings = TypeUtil.binding(field, parentTypeBindings);
        } else if (generics.length > 0) {
            typeBindings = TypeUtil.binding((Class) jdkType, generics);
        } else {
            typeBindings = TypeUtil.binding(jdkType, parentTypeBindings);
        }
    }

    public FieldAccessor getAccessor() {
        return accessor;
    }

    public Field getField() {
        if (accessor == null) {
            return null;
        }
        return accessor.getField();
    }

    public TypeHandlerFactory getTypeHandlerFactory() {
        return typeHandlerFactory;
    }

    public TypeBindings getTypeBindings() {
        return typeBindings;
    }

    public void setTypeBindings(TypeBindings typeBindings) {
        this.typeBindings = typeBindings;
    }

    public Type getJdkType() {
        return jdkType;
    }

    public List<TypeHandler> getHandlers() {
        return handlers;
    }

    public void writeBool(boolean value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeBool(value, context);
    }

    public void writeInt(int value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeInt(value, context);
    }

    public void writeLong(long value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeLong(value, context);
    }

    public void writeFloat(float value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeFloat(value, context);
    }

    public void writeDouble(double value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeDouble(value, context);
    }

    public void writeBytes(byte[] bytes, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(bytes, context)) {
            typeWriter.writeBytes(bytes, context);
        }
    }

    public void writeString(String s, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(s, context)) {
            typeWriter.writeString(s, context);
        }
    }

    public boolean readBoolean(TypeReader typeReader, ReadContext context) {
        return typeReader.readBoolean(context);
    }

    public int readInt(TypeReader typeReader, ReadContext context) {
        return typeReader.readInt(context);
    }

    public long readLong(TypeReader typeReader, ReadContext context) {
        return typeReader.readLong(context);
    }

    public float readFloat(TypeReader typeReader, ReadContext context) {
        return typeReader.readFloat(context);
    }

    public double readDouble(TypeReader typeReader, ReadContext context) {
        return typeReader.readDouble(context);
    }

    public byte[] readBytes(TypeReader typeReader, ReadContext context) {
        return typeReader.readBytes(context);
    }

    public String readString(TypeReader typeReader, ReadContext context) {
        return typeReader.readString(context);
    }

    public abstract void writeValue(Object value, TypeWriter typeWriter, WriteContext context);

    public abstract void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context);

    public abstract Object readValue(TypeReader typeReader, ReadContext context);

    public abstract void readIntoField(Object parent, TypeReader typeReader, ReadContext context);

    public abstract boolean requiresTypeColumn();

    public abstract String name();
}