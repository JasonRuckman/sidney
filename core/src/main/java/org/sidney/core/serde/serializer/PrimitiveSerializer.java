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

import org.sidney.core.Accessors;
import org.sidney.core.Encode;
import org.sidney.core.TypeRef;
import org.sidney.core.io.Encoding;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.WriteContext;

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
    private Encoding encoding;
    private Class<?> rawClass;

    @Override
    public void consume(TypeRef typeRef, SerializerContext context) {
        writer = WRITERS.get(typeRef.getType());
        reader = READERS.get(typeRef.getType());
        if (typeRef instanceof TypeRef.TypeFieldRef &&
                ((TypeRef.TypeFieldRef) typeRef).getJdkField().getAnnotation(Encode.class) != null) {
            encoding = ((TypeRef.TypeFieldRef) typeRef).getJdkField().getAnnotation(Encode.class).value();
        }
        rawClass = typeRef.getType();
    }

    @Override
    public void writeValue(Object value, WriteContext context) {
        if (context.writeNullMarker(value)) {
            writer.writeValue(value, context);
        }
        context.incrementColumnIndex();
    }

    @Override
    public Object readValue(ReadContext context) {
        if (context.readNullMarker()) {
            Object value = reader.readValue(context);
            context.incrementColumnIndex();
            return value;
        }
        context.incrementColumnIndex();
        return null;
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    public org.sidney.core.serde.Type getType() {
        return TYPES.get(rawClass);
    }

    public Encoding getEncoding() {
        if (encoding != null) {
            return encoding;
        }
        return getType().defaultEncoding();
    }

    public static class NonNullPrimitiveSerializer extends PrimitiveSerializer {
        @Override
        public void writeFromField(Object parent, WriteContext context) {
            writer.writeFromField(parent, context, getAccessor());
            context.incrementColumnIndex();
        }

        @Override
        public void readIntoField(Object parent, ReadContext context) {
            reader.readIntoField(parent, context, getAccessor());
            context.incrementColumnIndex();
        }
    }

    public static class PrimitiveWriters {
        static abstract class PrimitiveWriter {
            public void writeValue(Object value, WriteContext context) {
                throw new IllegalStateException();
            }

            public abstract void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor);
        }

        static class BoolWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeBool(accessor.getBoolean(parent));
            }
        }

        static class ByteWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeByte(accessor.getByte(parent));
            }
        }

        static class ShortWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeShort(accessor.getShort(parent));
            }
        }

        static class CharWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeChar(accessor.getChar(parent));
            }
        }

        static class IntWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeInt(accessor.getInt(parent));
            }
        }

        static class LongWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeLong(accessor.getLong(parent));
            }
        }

        static class FloatWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeFloat(accessor.getFloat(parent));
            }
        }

        static class DoubleWriter extends PrimitiveWriter {
            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeDouble(accessor.getDouble(parent));
            }
        }

        static class StringWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                context.writeString((String) value);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeString((String) accessor.get(parent));
            }
        }

        static class BytesWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                context.writeBytes((byte[]) value);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                context.writeBytes((byte[]) accessor.get(parent));
            }
        }

        static class BoolRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                context.writeBool((Boolean) value);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeBool((Boolean) accessor.get(parent), context);
            }

            private void writeBool(Boolean value, WriteContext context) {
                context.writeBool(value);
            }
        }

        static class ByteRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                writeByte((Byte) value, context);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeByte((Byte) accessor.get(parent), context);
            }

            private void writeByte(Byte value, WriteContext context) {
                context.writeByte(value);
            }
        }

        static class ShortRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                writeShort((Short) value, context);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeShort((Short) accessor.get(parent), context);
            }

            private void writeShort(Short value, WriteContext context) {
                context.writeShort(value);
            }
        }

        static class CharRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                writeChar((Character) value, context);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeChar((Character) accessor.get(parent), context);
            }

            private void writeChar(Character value, WriteContext context) {
                context.writeChar(value);
            }
        }

        static class IntRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                writeInteger((Integer) value, context);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeInteger((Integer) accessor.get(parent), context);
            }

            private void writeInteger(Integer value, WriteContext context) {
                context.writeInt(value);
            }
        }

        static class LongRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                writeLong((Long) value, context);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeLong((Long) accessor.get(parent), context);
            }

            private void writeLong(Long value, WriteContext context) {
                context.writeLong(value);
            }
        }

        static class FloatRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                writeFloat((Float) value, context);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeFloat((Float) accessor.get(parent), context);
            }

            private void writeFloat(Float value, WriteContext context) {
                context.writeFloat(value);
            }
        }

        static class DoubleRefWriter extends PrimitiveWriter {
            @Override
            public void writeValue(Object value, WriteContext context) {
                writeDouble((Double) value, context);
            }

            @Override
            public void writeFromField(Object parent, WriteContext context, Accessors.FieldAccessor accessor) {
                writeDouble((Double) accessor.get(parent), context);
            }

            private void writeDouble(Double value, WriteContext context) {
                context.writeDouble(value);
            }
        }
    }

    public static class PrimitiveReaders {
        public static abstract class PrimitiveReader {
            public Object readValue(ReadContext context) {
                throw new IllegalStateException();
            }

            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.set(parent, readValue(context));
            }
        }

        public static class BoolPrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setBoolean(parent, context.readBoolean());
            }
        }

        public static class BytePrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setByte(parent, context.readByte());
            }
        }

        public static class ShortPrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setShort(parent, context.readShort());
            }
        }

        public static class CharPrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setChar(parent, context.readChar());
            }
        }

        public static class IntPrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setInt(parent, context.readInt());
            }
        }

        public static class LongPrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setLong(parent, context.readLong());
            }
        }

        public static class FloatPrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setFloat(parent, context.readFloat());
            }
        }

        public static class DoublePrimitiveReader extends PrimitiveReader {
            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.setDouble(parent, context.readDouble());
            }
        }

        public static class BytesPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readBytes();
            }

            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.set(parent, readValue(context));
            }
        }

        public static class StringPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readString();
            }

            @Override
            public void readIntoField(Object parent, ReadContext context, Accessors.FieldAccessor accessor) {
                accessor.set(parent, readValue(context));
            }
        }

        public static class BoolRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readBoolean();
            }
        }

        public static class ByteRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readByte();
            }
        }

        public static class CharRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readChar();
            }
        }

        public static class ShortRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readShort();
            }
        }

        public static class IntRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readInt();
            }
        }

        public static class LongRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readLong();
            }
        }

        public static class FloatRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readFloat();
            }
        }

        public static class DoubleRefPrimitiveReader extends PrimitiveReader {
            @Override
            public Object readValue(ReadContext context) {
                return context.readDouble();
            }
        }
    }
}