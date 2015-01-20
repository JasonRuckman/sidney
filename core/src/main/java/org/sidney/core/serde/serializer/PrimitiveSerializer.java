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
import org.sidney.core.Encode;
import org.sidney.core.io.Encoding;
import org.sidney.core.serde.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PrimitiveSerializer extends Serializer {
    private static final Map<Class, PrimitiveWriters.PrimitiveWriter> WRITERS = new HashMap<>();
    private static final Map<Class, org.sidney.core.serde.Type> TYPES = new HashMap<>();
    private static final Map<Class, PrimitiveReaders.PrimitiveReader> READERS = new HashMap<>();

    static {
        WRITERS.put(boolean.class, new PrimitiveWriters.BoolWriter());
        WRITERS.put(Boolean.class, new PrimitiveWriters.BoolRefWriter());
        WRITERS.put(byte.class, new PrimitiveWriters.ByteWriter());
        WRITERS.put(Byte.class, new PrimitiveWriters.ByteRefWriter());
        WRITERS.put(char.class, new PrimitiveWriters.CharWriter());
        WRITERS.put(Character.class, new PrimitiveWriters.CharRefWriter());
        WRITERS.put(short.class, new PrimitiveWriters.ShortWriter());
        WRITERS.put(Short.class, new PrimitiveWriters.ShortRefWriter());
        WRITERS.put(int.class, new PrimitiveWriters.IntWriter());
        WRITERS.put(Integer.class, new PrimitiveWriters.IntRefWriter());
        WRITERS.put(long.class, new PrimitiveWriters.LongWriter());
        WRITERS.put(Long.class, new PrimitiveWriters.LongRefWriter());
        WRITERS.put(float.class, new PrimitiveWriters.FloatWriter());
        WRITERS.put(Float.class, new PrimitiveWriters.FloatRefWriter());
        WRITERS.put(double.class, new PrimitiveWriters.DoubleWriter());
        WRITERS.put(Double.class, new PrimitiveWriters.DoubleRefWriter());
        WRITERS.put(byte[].class, new PrimitiveWriters.BytesWriter());
        WRITERS.put(String.class, new PrimitiveWriters.StringWriter());

        READERS.put(boolean.class, new PrimitiveReaders.BoolPrimitiveReader());
        READERS.put(Boolean.class, new PrimitiveReaders.BoolRefPrimitiveReader());
        READERS.put(byte.class, new PrimitiveReaders.BytePrimitiveReader());
        READERS.put(Byte.class, new PrimitiveReaders.ByteRefPrimitiveReader());
        READERS.put(short.class, new PrimitiveReaders.ShortPrimitiveReader());
        READERS.put(Short.class, new PrimitiveReaders.ShortRefPrimitiveReader());
        READERS.put(char.class, new PrimitiveReaders.CharPrimitiveReader());
        READERS.put(Character.class, new PrimitiveReaders.CharRefPrimitiveReader());
        READERS.put(int.class, new PrimitiveReaders.IntPrimitiveReader());
        READERS.put(Integer.class, new PrimitiveReaders.IntRefPrimitiveReader());
        READERS.put(long.class, new PrimitiveReaders.LongPrimitiveReader());
        READERS.put(Long.class, new PrimitiveReaders.LongRefPrimitiveReader());
        READERS.put(float.class, new PrimitiveReaders.FloatPrimitiveReader());
        READERS.put(Float.class, new PrimitiveReaders.FloatRefPrimitiveReader());
        READERS.put(double.class, new PrimitiveReaders.DoublePrimitiveReader());
        READERS.put(Double.class, new PrimitiveReaders.DoubleRefPrimitiveReader());
        READERS.put(byte[].class, new PrimitiveReaders.BytesPrimitiveReader());
        READERS.put(String.class, new PrimitiveReaders.StringPrimitiveReader());

        TYPES.put(boolean.class, org.sidney.core.serde.Type.BOOLEAN);
        TYPES.put(Boolean.class, org.sidney.core.serde.Type.BOOLEAN);
        TYPES.put(byte.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(Byte.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(int.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(Integer.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(short.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(Short.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(char.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(Character.class, org.sidney.core.serde.Type.INT32);
        TYPES.put(long.class, org.sidney.core.serde.Type.INT64);
        TYPES.put(Long.class, org.sidney.core.serde.Type.INT64);
        TYPES.put(float.class, org.sidney.core.serde.Type.FLOAT32);
        TYPES.put(Float.class, org.sidney.core.serde.Type.FLOAT32);
        TYPES.put(double.class, org.sidney.core.serde.Type.FLOAT64);
        TYPES.put(Double.class, org.sidney.core.serde.Type.FLOAT64);
        TYPES.put(byte[].class, org.sidney.core.serde.Type.BINARY);
        TYPES.put(String.class, org.sidney.core.serde.Type.STRING);
    }
    protected PrimitiveWriters.PrimitiveWriter writer;
    protected PrimitiveReaders.PrimitiveReader reader;
    private Class<?> actualType;

    public PrimitiveSerializer(Type jdkType, Field field, TypeBindings parentTypeBindings, Serializers serializers) {
        super(jdkType, field, parentTypeBindings, serializers);

        actualType = (Class<?>) jdkType;

        writer = WRITERS.get(actualType);
        reader = READERS.get(actualType);
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(value, context)) {
            writer.writeValue(value, typeWriter, context);
        }
        context.incrementColumnIndex();
    }

    @Override
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        Object value = getAccessor().get(parent);
        writeValue(value, typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            Object value = reader.readValue(typeReader, context);
            context.incrementColumnIndex();
            return value;
        }
        context.incrementColumnIndex();
        return null;
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        getAccessor().set(parent, readValue(typeReader, context));
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    public Class<?> getActualType() {
        return actualType;
    }

    public org.sidney.core.serde.Type getType() {
        return TYPES.get(getActualType());
    }

    public Encoding getEncoding() {
        if (getField() != null && getField().getAnnotation(Encode.class) != null) {
            return getField().getAnnotation(Encode.class).value();
        }
        return Encoding.PLAIN;
    }

    public static class PrimitiveSerializerFactory extends SerializerFactory {
        @Override
        public Serializer newSerializer(Type type, Field field, TypeBindings typeBindings, Serializers serializers) {
            return new PrimitiveSerializer(type, field, typeBindings, serializers);
        }
    }

    public static class NonNullPrimitiveSerializerFactory extends SerializerFactory {
        @Override
        public Serializer newSerializer(Type type, Field field, TypeBindings typeBindings, Serializers serializers) {
            return new NonNullPrimitiveSerializer(type, field, typeBindings, serializers);
        }
    }

    static class NonNullPrimitiveSerializer extends PrimitiveSerializer {
        public NonNullPrimitiveSerializer(Type jdkType, Field field, TypeBindings parentTypeBindings, Serializers serializers) {
            super(jdkType, field, parentTypeBindings, serializers);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
            writer.writeFromField(parent, typeWriter, context, getAccessor());
            context.incrementColumnIndex();
        }

        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
            reader.readIntoField(parent, typeReader, context, getAccessor());
            context.incrementColumnIndex();
        }
    }
}