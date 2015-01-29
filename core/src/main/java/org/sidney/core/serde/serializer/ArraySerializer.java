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

import org.sidney.core.TypeRef;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class ArraySerializer extends Serializer<Object> {
    private static final Map<Class, Arrays.ArrayWriters.ArrayWriter> PRIMITIVE_WRITERS = new HashMap<>();
    private static final Map<Class, Arrays.ArrayReaders.ArrayReader> PRIMITIVE_READERS = new HashMap<>();

    static {
        PRIMITIVE_WRITERS.put(boolean[].class, new Arrays.ArrayWriters.BoolArrayWriter());
        PRIMITIVE_WRITERS.put(char[].class, new Arrays.ArrayWriters.CharArrayWriter());
        PRIMITIVE_WRITERS.put(short[].class, new Arrays.ArrayWriters.ShortArrayWriter());
        PRIMITIVE_WRITERS.put(int[].class, new Arrays.ArrayWriters.IntArrayWriter());
        PRIMITIVE_WRITERS.put(long[].class, new Arrays.ArrayWriters.LongArrayWriter());
        PRIMITIVE_WRITERS.put(float[].class, new Arrays.ArrayWriters.FloatArrayWriter());
        PRIMITIVE_WRITERS.put(double[].class, new Arrays.ArrayWriters.DoubleArrayWriter());

        PRIMITIVE_READERS.put(boolean[].class, new Arrays.ArrayReaders.BoolArrayReader());
        PRIMITIVE_READERS.put(char[].class, new Arrays.ArrayReaders.CharArrayReader());
        PRIMITIVE_READERS.put(short[].class, new Arrays.ArrayReaders.ShortArrayReader());
        PRIMITIVE_READERS.put(int[].class, new Arrays.ArrayReaders.IntArrayReader());
        PRIMITIVE_READERS.put(long[].class, new Arrays.ArrayReaders.LongArrayReader());
        PRIMITIVE_READERS.put(float[].class, new Arrays.ArrayReaders.FloatArrayReader());
        PRIMITIVE_READERS.put(double[].class, new Arrays.ArrayReaders.DoubleArrayReader());
    }

    private Serializer contentSerializer;
    private Arrays.ArrayWriters.ArrayWriter arrayWriter;
    private Arrays.ArrayReaders.ArrayReader arrayReader;
    private Class<?> rawClass;

    @Override
    public void consume(TypeRef typeRef, SerializerContext context) {
        arrayWriter = PRIMITIVE_WRITERS.get(typeRef.getType());
        if (arrayWriter == null) {
            arrayWriter = new RefArrayWriter();
        }
        arrayReader = PRIMITIVE_READERS.get(typeRef.getType());
        if (arrayReader == null) {
            arrayReader = new RefArrayReader();
        }
        contentSerializer = context.serializer(typeRef.getTypeParameters().get(0), this);
        rawClass = typeRef.getType();
    }

    @Override
    public void writeValue(Object value, WriteContext context) {
        writeArray(value, context);
    }

    @Override
    public Object readValue(ReadContext context) {
        return readArray(context);
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    private void writeArray(Object array, WriteContext context) {
        if (context.writeNullMarker(array)) {
            context.writeRepetitionCount(context.getColumnIndex(), Array.getLength(array));
            //bump to component column
            context.incrementColumnIndex();
            arrayWriter.writeArray(array, context);
            context.incrementColumnIndex();
        } else {
            context.incrementColumnIndex(getNumFieldsToIncrementBy() + 1);
        }
    }

    private Object readArray(ReadContext context) {
        if (context.readNullMarker()) {
            int arraySize = context.readRepetitionCount();
            Object array = Array.newInstance(rawClass.getComponentType(), arraySize);
            context.incrementColumnIndex();
            arrayReader.readValue(context, array);
            context.incrementColumnIndex();
            return array;
        }
        context.incrementColumnIndex(getNumFieldsToIncrementBy() + 1);
        return null;
    }

    public static class Arrays {
        public static class ArrayReaders {
            public abstract static interface ArrayReader<T> {
                void readValue(ReadContext context, T newArray);
            }

            public static class BoolArrayReader implements ArrayReader<boolean[]> {
                @Override
                public void readValue(ReadContext context, boolean[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = context.readBoolean();
                    }
                }
            }

            public static class CharArrayReader implements ArrayReader<char[]> {
                @Override
                public void readValue(ReadContext context, char[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = context.readChar();
                    }
                }
            }

            public static class ShortArrayReader implements ArrayReader<short[]> {
                @Override
                public void readValue(ReadContext context, short[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = context.readShort();
                    }
                }
            }

            public static class IntArrayReader implements ArrayReader<int[]> {
                @Override
                public void readValue(ReadContext context, int[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = context.readInt();
                    }
                }
            }

            public static class LongArrayReader implements ArrayReader<long[]> {
                @Override
                public void readValue(ReadContext context, long[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = context.readLong();
                    }
                }
            }

            public static class FloatArrayReader implements ArrayReader<float[]> {
                @Override
                public void readValue(ReadContext context, float[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = context.readFloat();
                    }
                }
            }

            public static class DoubleArrayReader implements ArrayReader<double[]> {
                @Override
                public void readValue(ReadContext context, double[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = context.readDouble();
                    }
                }
            }
        }

        public static class ArrayWriters {
            public static interface ArrayWriter<T> {
                void writeArray(T value, WriteContext context);
            }

            public static class BoolArrayWriter implements ArrayWriter<boolean[]> {
                @Override
                public void writeArray(boolean[] value, WriteContext context) {
                    for (boolean b : value) {
                        context.writeBool(b);
                    }
                }
            }

            public static class ShortArrayWriter implements ArrayWriter<short[]> {
                @Override
                public void writeArray(short[] value, WriteContext context) {
                    for (short s : value) {
                        context.writeShort(s);
                    }
                }
            }

            public static class CharArrayWriter implements ArrayWriter<char[]> {
                @Override
                public void writeArray(char[] value, WriteContext context) {
                    for (char c : value) {
                        context.writeChar(c);
                    }
                }
            }

            public static class IntArrayWriter implements ArrayWriter<int[]> {
                @Override
                public void writeArray(int[] value, WriteContext context) {
                    for (int i : value) {
                        context.writeInt(i);
                    }
                }
            }

            public static class LongArrayWriter implements ArrayWriter<long[]> {
                @Override
                public void writeArray(long[] value, WriteContext context) {
                    for (long l : value) {
                        context.writeLong(l);
                    }
                }
            }

            public static class FloatArrayWriter implements ArrayWriter<float[]> {
                @Override
                public void writeArray(float[] value, WriteContext context) {
                    for (float f : value) {
                        context.writeFloat(f);
                    }
                }
            }

            public static class DoubleArrayWriter implements ArrayWriter<double[]> {
                @Override
                public void writeArray(double[] value, WriteContext context) {
                    for (double d : value) {
                        context.writeDouble(d);
                    }
                }
            }
        }
    }

    private class RefArrayWriter implements Arrays.ArrayWriters.ArrayWriter<Object[]> {
        @Override
        public void writeArray(Object[] value, WriteContext context) {
            int index = context.getColumnIndex();
            for (Object o : value) {
                contentSerializer.writeValue(o, context);
                context.setColumnIndex(index); //rewind back to start of component type
            }
        }
    }

    private class RefArrayReader implements Arrays.ArrayReaders.ArrayReader<Object[]> {
        @Override
        public void readValue(ReadContext context, Object[] newArray) {
            int index = context.getColumnIndex();
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = contentSerializer.readValue(context);
                context.setColumnIndex(index);
            }
        }
    }
}