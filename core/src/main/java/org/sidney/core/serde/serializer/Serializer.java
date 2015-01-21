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
import org.sidney.core.FieldAccessor;
import org.sidney.core.ReflectionFieldAccessor;
import org.sidney.core.serde.*;

import java.lang.reflect.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles serializing a given type, is responsible for decomposing that type and constructing sub serializers if necessary
 */
public abstract class Serializer<T> {
    protected int numSubFields = 1;
    protected Class[] generics;
    private List<Serializer> serializers = new ArrayList<>();
    private Type jdkType;
    private Field field;
    private TypeBindings parentTypeBindings;
    private FieldAccessor accessor;
    private SerializerRepository serializerRepository;
    private TypeBindings typeBindings;

    public Serializer(Type jdkType,
                      Field field,
                      TypeBindings parentTypeBindings,
                      SerializerRepository serializerRepository, Class... generics) {
        this.jdkType = jdkType;
        this.field = field;
        this.parentTypeBindings = parentTypeBindings;
        this.accessor = (field != null) ? new ReflectionFieldAccessor(field) : null;
        this.serializerRepository = serializerRepository;
        this.generics = generics;

        resolveTypeBindings();

        if (ParameterizedType.class.isAssignableFrom(getJdkType().getClass())) {
            getSerializers().addAll(fromParameterizedType((ParameterizedType) getJdkType()));
        } else if (TypeVariable.class.isAssignableFrom(getJdkType().getClass())) {
            TypeVariable variable = (TypeVariable) getJdkType();
            getSerializers().addAll(fromTypeVariable(variable));
        } else if (getJdkType().getClass() == Class.class && generics.length > 0) {
            getSerializers().addAll(fromParameterizedClass((Class) getJdkType(), generics));
        } else if (GenericArrayType.class.isAssignableFrom(getJdkType().getClass())) {
            getSerializers().addAll(fromArrayType((GenericArrayType) getJdkType()));
        } else {
            getSerializers().addAll(fromType(getJdkType()));
        }
    }

    /**
     * Get the type bindings for the object that contains this one
     */
    public TypeBindings getParentTypeBindings() {
        return parentTypeBindings;
    }

    /**
     * Returns the configured type serializer factory for this object tree
     */
    public SerializerRepository getSerializerRepository() {
        return serializerRepository;
    }

    /**
     * Returns the type bindings for this level
     */
    public TypeBindings getTypeBindings() {
        return typeBindings;
    }

    public void setTypeBindings(TypeBindings typeBindings) {
        this.typeBindings = typeBindings;
    }

    /**
     * Return the jdk type at this level
     */
    public Type getJdkType() {
        return jdkType;
    }

    /**
     * All serializers for this level and below
     * @return all type serializers including this level and all sub levels
     */
    public List<Serializer> getSerializers() {
        return serializers;
    }

    /**
     * Write a boolean value directly to the current column
     */
    public void writeBool(boolean value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeBool(value, context);
    }

    /**
     * Write an int directly to the current column
     */
    public void writeInt(int value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeInt(value, context);
    }

    /**
     * Write an long directly to the current column
     */
    public void writeLong(long value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeLong(value, context);
    }

    /**
     * Write a float directly to the current column
     */
    public void writeFloat(float value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeFloat(value, context);
    }

    /**
     * Write double directly to the current column
     */
    public void writeDouble(double value, TypeWriter typeWriter, WriteContext context) {
        typeWriter.writeDouble(value, context);
    }

    /**
     * Write bytes directly to the current column
     */
    public void writeBytes(byte[] bytes, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(bytes, context)) {
            typeWriter.writeBytes(bytes, context);
        }
    }

    /**
     * Write string directly to the current column
     */
    public void writeString(String s, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(s, context)) {
            typeWriter.writeString(s, context);
        }
    }

    /**
     * Read a boolean from the current column
     */
    public boolean readBoolean(TypeReader typeReader, ReadContext context) {
        return typeReader.readBoolean(context);
    }

    /**
     * Read a int from the current column
     */
    public int readInt(TypeReader typeReader, ReadContext context) {
        return typeReader.readInt(context);
    }

    /**
     * Read a long from the current column
     */
    public long readLong(TypeReader typeReader, ReadContext context) {
        return typeReader.readLong(context);
    }

    /**
     * Read a float from the current column
     */
    public float readFloat(TypeReader typeReader, ReadContext context) {
        return typeReader.readFloat(context);
    }

    /**
     * Read a double from the current column
     */
    public double readDouble(TypeReader typeReader, ReadContext context) {
        return typeReader.readDouble(context);
    }

    /**
     * Read bytes from the current column
     */
    public byte[] readBytes(TypeReader typeReader, ReadContext context) {
        return typeReader.readBytes(context);
    }

    /**
     * Read a string from the current column
     */
    public String readString(TypeReader typeReader, ReadContext context) {
        return typeReader.readString(context);
    }

    /**
     * Fully consume {@param value}
     * The {@link org.sidney.core.serde.WriteContext#getColumnIndex()} on {@param context} must be incremented to the number of fields + subfields + 1 after the value is consumed
     * For example, if {@param value} is a bean with two int fields, it must be incremented by 4, one for the bean, two for the ints,
     * and one more to advance into the next field
     */
    public abstract void writeValue(Object value, TypeWriter typeWriter, WriteContext context);

    /**
     * Fully consume a field value from the parent, parent is guaranteed to be non-null
     * Follow the same incrementing rules as {@link #writeValue}
     */
    public abstract void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context);

    /**
     * Materialize a value from sub columns, columns must be incremented and read in the same order as they were written
     * in either {@link #writeValue} or {@link #writeFromField}
     * @return a fully materialized value
     */
    public abstract Object readValue(TypeReader typeReader, ReadContext context);

    /**
     * Materialize a value from sub columns, columns must be incremented and read in the same order as they were written
     * in either {@link #writeValue} or {@link #writeFromField} and the materialized value must be written into the field of the parent
     */
    public abstract void readIntoField(Object parent, TypeReader typeReader, ReadContext context);

    /**
     * Denotes whether or not the value requires serializing the type e.g {@link java.util.Map} so that a reader knows what constructor to use
     * @return whether or not a type column is required
     */
    public abstract boolean requiresTypeColumn();

    protected List<Serializer> fromType(Type type) {
        throw new IllegalStateException();
    }

    protected List<Serializer> fromArrayType(GenericArrayType type) {
        throw new IllegalStateException();
    }

    protected List<Serializer> fromParameterizedClass(Class<?> clazz, Class... types) {
        throw new IllegalStateException();
    }

    protected List<Serializer> fromParameterizedType(ParameterizedType type) {
        throw new IllegalStateException();
    }

    protected List<Serializer> fromTypeVariable(TypeVariable typeVariable) {
        throw new IllegalStateException();
    }

    protected FieldAccessor getAccessor() {
        return accessor;
    }

    protected Field getField() {
        if (accessor == null) {
            return null;
        }
        return accessor.getField();
    }

    protected void resolveTypeBindings() {
        if (field != null) {
            typeBindings = Types.binding(field, parentTypeBindings);
        } else if (generics.length > 0) {
            typeBindings = Types.binding((Class) jdkType, generics);
        } else {
            typeBindings = Types.binding(jdkType, parentTypeBindings);
        }
    }
}