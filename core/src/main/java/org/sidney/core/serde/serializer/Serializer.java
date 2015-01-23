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
 *
 * Directions for creating a serializer:
 * 1. Do not create parameterized constructors, serializers must have default constructors
 * 2. Override the writeValue and readValue methods, optionally override the writeFromField or readIntoField methods if the defaults won't work for you.
 *    It should be rare that the default methods don't work (primitives are the only case I can think of where you wouldn't use them)
 * 3. If your class takes generic arguments, implement the {@link #initFromParameterizedClass} and {@link #initFromParameterizedType} methods
 * 4. Implement the {@link #serializers} method to return any sub serializers you created (only this level).
 */
public abstract class Serializer<T> {
    private Class[] typeParams;
    private int numFieldsToIncrementBy = 1;
    private List<Serializer> serializers = new ArrayList<>();
    private Type jdkType;
    private Field field;
    private TypeBindings parentTypeBindings;
    private Accessors.FieldAccessor accessor;
    private SerializerRepository serializerRepository;
    private TypeBindings typeBindings;
    private Class<T> rawClass;

    public Serializer() {

    }
    /**
     * Get the type params for thsi serializer, if this is a parameterized class
     */
    public Class[] getTypeParams() {
        return typeParams;
    }

    /**
     * Set the type params for this serializer, if this is a parameterized class
     */
    void setTypeParams(Class[] typeParams) {
        this.typeParams = typeParams;
    }

    /**
     * Indicates the number of fields to increment by if this value is null, includes all subfields + 1
     */
    public int getNumFieldsToIncrementBy() {
        return numFieldsToIncrementBy;
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
     * Returns the configured type serializer repository
     */
    public SerializerRepository getSerializerRepository() {
        return serializerRepository;
    }

    /**
     * Set the configured type serializer repository
     */
    void setSerializerRepository(SerializerRepository serializerRepository) {
        this.serializerRepository = serializerRepository;
    }

    /**
     * Returns the type bindings for this level
     */
    public TypeBindings getTypeBindings() {
        return typeBindings;
    }

    /**
     * Return the jdk type at this level
     */
    public Type getJdkType() {
        return jdkType;
    }

    /**
     * Return the jdk type for this level
     */
    void setJdkType(Type jdkType) {
        this.jdkType = jdkType;
    }

    /**
     * Get the type bindings for the object that contains this one
     */
    public TypeBindings getParentTypeBindings() {
        return parentTypeBindings;
    }

    /**
     * Set parent type bindings for this type
     */
    void setParentTypeBindings(TypeBindings parentTypeBindings) {
        this.parentTypeBindings = parentTypeBindings;
    }

    /**
     * Do type resolution and delegate to impl on handling of various types
     */
    public final void init() {
        if (ParameterizedType.class.isAssignableFrom(getJdkType().getClass())) {
            rawClass = (Class) Types.type(getJdkType(), getParentTypeBindings()).getRawClass();
            initFromParameterizedType((ParameterizedType) getJdkType());
        } else if (TypeVariable.class.isAssignableFrom(getJdkType().getClass())) {
            rawClass = (Class) Types.type(getJdkType(), getParentTypeBindings()).getRawClass();
            TypeVariable variable = (TypeVariable) getJdkType();
            initFromTypeVariable(variable);
        } else if (getJdkType().getClass() == Class.class && typeParams.length > 0) {
            rawClass = (Class) Types.parameterizedType((Class) getJdkType(), typeParams).getRawClass();
            initFromParameterizedClass((Class) getJdkType(), typeParams);
        } else if (GenericArrayType.class.isAssignableFrom(getJdkType().getClass())) {
            rawClass = (Class) Types.type(getJdkType(), getParentTypeBindings()).getRawClass();
            initFromArrayType((GenericArrayType) getJdkType());
        } else {
            rawClass = (Class) getJdkType();
            initFromClass(rawClass);
        }
    }

    /**
     * Called after all types are resolved but before sub serializers are created.
     * Override this method to do any cleanup before serializers are created
     */
    public void postInit() {

    }

    /**
     * Final stage in serializer initialization, creates all sub serializers and calculates their field counts
     */
    public final void finish() {
        for(Serializer serializer : serializersAtThisLevel()) {
            serializers.add(serializer);
            serializers.addAll(serializer.getSerializers());

            numFieldsToIncrementBy += serializer.getNumFieldsToIncrementBy();
        }
    }

    /**
     * Resolve any type bindings given the context
     */
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


    /**
     * Return any serializers created for this level only
     * @return serializers at this level
     */
    protected abstract List<Serializer> serializersAtThisLevel();

    /**
     * Initialize from a
     * @param type
     */
    protected void initFromClass(Class type) {

    }

    protected void initFromArrayType(GenericArrayType type) {

    }

    protected void initFromParameterizedClass(Class<?> clazz, Class... types) {

    }

    protected void initFromParameterizedType(ParameterizedType type) {

    }

    protected void initFromTypeVariable(TypeVariable typeVariable) {

    }

    /**
     * Get the accessor for this field, if present, otherwise null
     * @return the accessor
     */
    protected final Accessors.FieldAccessor getAccessor() {
        return accessor;
    }

    /**
     * Get the field for this serializer, if present, otherwise null
     * @return the field
     */
    protected final Field getField() {
        if (accessor == null) {
            return null;
        }
        return accessor.getField();
    }

    void setField(Field field) {
        this.field = field;
        if (field != null) {
            accessor = Accessors.newAccessor(field);
        }
    }
}