package org.sidney.core.serde.serializer;

import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public abstract class GenericSerializerFactory extends SerializerFactory {
    public abstract <T> GenericSerializer<T> newSerializer(Type type,
                                                    Field field,
                                                    TypeBindings typeBindings, Serializers serializers, Class[] typeParameters);
}