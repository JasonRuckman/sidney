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
import org.sidney.core.Accessors;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles serializing a given type, is responsible for decomposing that type and constructing sub serializers if necessary
 */
public abstract class Serializer<T> {
    private Class[] typeParams;
    private int numAllFields = 1;
    private List<Serializer> serializers = new ArrayList<>();
    private Type jdkType;
    private Field field;
    private TypeBindings parentTypeBindings;
    private Accessors.FieldAccessor accessor;
    private SerializerRepository serializerRepository;
    private TypeBindings typeBindings;
    private Class<T> rawClass;

    public Class[] getTypeParams() {
        return typeParams;
    }

    public void setTypeParams(Class[] typeParams) {
        this.typeParams = typeParams;
    }

    public int getNumFields() {
        return numAllFields;
    }

    /**
     * Get the raw class derived from whatever type info is available
     */
    public final Class<T> getRawClass() {
        return rawClass;
    }

    /**
     * All serializers for this level and below
     *
     * @return all type serializers including this level and all sub levels
     */
    public List<Serializer> getSerializers() {
        return serializers;
    }

    /**
     * Returns the configured type serializer factory for this object tree
     */
    public SerializerRepository getSerializerRepository() {
        return serializerRepository;
    }

    public void setSerializerRepository(SerializerRepository serializerRepository) {
        this.serializerRepository = serializerRepository;
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

    public void setJdkType(Type jdkType) {
        this.jdkType = jdkType;
    }

    /**
     * Get the type bindings for the object that contains this one
     */
    public TypeBindings getParentTypeBindings() {
        return parentTypeBindings;
    }

    public void setParentTypeBindings(TypeBindings parentTypeBindings) {
        this.parentTypeBindings = parentTypeBindings;
    }

    public void addToFieldCount(int subFieldCount) {
        numAllFields += subFieldCount;
    }

    public final void preInit() {
        if (ParameterizedType.class.isAssignableFrom(getJdkType().getClass()) ||
                TypeVariable.class.isAssignableFrom(getJdkType().getClass()) ||
                GenericArrayType.class.isAssignableFrom(getJdkType().getClass())) {
            rawClass = (Class) Types.type(getJdkType(), getParentTypeBindings()).getRawClass();
        } else if (getJdkType().getClass() == Class.class && typeParams.length > 0) {
            rawClass = (Class) Types.parameterizedType((Class) getJdkType(), typeParams).getRawClass();
        } else {
            rawClass = (Class) getJdkType();
        }
    }

    public final void init() {
        if (ParameterizedType.class.isAssignableFrom(getJdkType().getClass())) {
            initFromParameterizedType((ParameterizedType) getJdkType());
        } else if (TypeVariable.class.isAssignableFrom(getJdkType().getClass())) {
            TypeVariable variable = (TypeVariable) getJdkType();
            initFromTypeVariable(variable);
        } else if (getJdkType().getClass() == Class.class && typeParams.length > 0) {
            initFromParameterizedClass((Class) getJdkType(), typeParams);
        } else if (GenericArrayType.class.isAssignableFrom(getJdkType().getClass())) {
            initFromArrayType((GenericArrayType) getJdkType());
        } else {
            initFromType(getJdkType());
        }
    }

    public void postInit() {

    }

    public final void finish() {
        serializers.addAll(
                serializers()
        );
    }

    public final void resolveTypeBindings() {
        if (field != null) {
            typeBindings = Types.binding(field, parentTypeBindings);
        } else if (typeParams.length > 0) {
            typeBindings = Types.binding((Class) jdkType, typeParams);
        } else {
            typeBindings = Types.binding(jdkType, parentTypeBindings);
        }
    }

    /**
     * Read bytes from the current column
     */
    public final byte[] readBytes(TypeReader typeReader, ReadContext context) {
        return typeReader.readBytes(context);
    }

    /**
     * Read a string from the current column
     */
    public final String readString(TypeReader typeReader, ReadContext context) {
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
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        writeValue(getAccessor().get(parent), typeWriter, context);
    }

    /**
     * Materialize a value from sub columns, columns must be incremented and read in the same order as they were written
     * in either {@link #writeValue} or {@link #writeFromField}
     *
     * @return a fully materialized value
     */
    public abstract Object readValue(TypeReader typeReader, ReadContext context);

    /**
     * Materialize a value from sub columns, columns must be incremented and read in the same order as they were written
     * in either {@link #writeValue} or {@link #writeFromField} and the materialized value must be written into the field of the parent
     */
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        getAccessor().set(parent, readValue(typeReader, context));
    }

    /**
     * Denotes whether or not the value requires serializing the type e.g {@link java.util.Map} so that a reader knows what constructor to use
     *
     * @return whether or not a type column is required
     */
    public abstract boolean requiresTypeColumn();

    protected abstract List<Serializer> serializers();

    protected void initFromType(Type type) {

    }

    protected void initFromArrayType(GenericArrayType type) {

    }

    protected void initFromParameterizedClass(Class<?> clazz, Class... types) {

    }

    protected void initFromParameterizedType(ParameterizedType type) {

    }

    protected void initFromTypeVariable(TypeVariable typeVariable) {

    }

    protected final Accessors.FieldAccessor getAccessor() {
        return accessor;
    }

    protected final Field getField() {
        if (accessor == null) {
            return null;
        }
        return accessor.getField();
    }

    public void setField(Field field) {
        this.field = field;
        if (field != null) {
            accessor = Accessors.newAccessor(field);
        }
    }
}