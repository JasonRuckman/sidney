package org.sidney.core.serde.serializer;

import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * type, field, typeBindings, this, generics
 * @param <T>
 */
public abstract class SerializerFactory<T extends Serializer> {
    public abstract T newSerializer(Type type, Field field, TypeBindings typeBindings, Serializers serializers);
}