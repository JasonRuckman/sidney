package org.sidney.core.serde.serializer;

import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public abstract class GenericSerializerFactory<T extends GenericSerializer> extends SerializerFactory<T> {
    public abstract T newSerializer(Type type, Field field, TypeBindings typeBindings, Serializers serializers, Class... typeParameters);
}