package org.sidney.core.serde.serializer;

import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * type, field, typeBindings, this, generics
 */
public abstract class SerializerFactory {
    public abstract <T> Serializer<T> newSerializer(Type type, Field field, TypeBindings typeBindings, SerializerRepository serializerRepository);
}