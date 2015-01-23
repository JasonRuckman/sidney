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

import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public void postInit() {
        arrayWriter = PRIMITIVE_WRITERS.get(getRawClass());
        if (arrayWriter == null) {
            arrayWriter = new RefArrayWriter();
        }
        arrayReader = PRIMITIVE_READERS.get(getRawClass());
        if (arrayReader == null) {
            arrayReader = new RefArrayReader();
        }
        addToFieldCount(contentSerializer.getNumFields());
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeArray(value, typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return readArray(typeReader, context);
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    @Override
    protected List<Serializer> serializers() {
        List<Serializer> serializers = new ArrayList<>();

        serializers.add(contentSerializer);
        serializers.addAll(contentSerializer.getSerializers());

        return serializers;
    }

    @Override
    protected void initFromType(Type type) {
        contentSerializer = getSerializerRepository().serializer(
                getRawClass().getComponentType(),
                null, getParentTypeBindings(), new Class[0]
        );
    }

    @Override
    protected void initFromArrayType(GenericArrayType type) {
        GenericArrayType genericArrayType = (GenericArrayType) getJdkType();
        contentSerializer = getSerializerRepository().serializer(
                genericArrayType.getGenericComponentType(), null, getParentTypeBindings(), new Class[0]
        );
    }

    @Override
    protected void initFromParameterizedClass(Class<?> clazz, Class... types) {
        Class<?> componentType = ((Class) getJdkType()).getComponentType();
        contentSerializer = getSerializerRepository().serializer(componentType, null, getTypeBindings(), types);
    }

    private void writeArray(Object array, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(array, context)) {
            typeWriter.writeRepetitionCount(context.getColumnIndex(), Array.getLength(array), context);
            //bump to component column
            context.incrementColumnIndex();
            arrayWriter.writeArray(array, typeWriter, context);
            context.incrementColumnIndex();
        } else {
            context.incrementColumnIndex(getNumFields() + 1);
        }
    }

    private Object readArray(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            int arraySize = typeReader.readRepetitionCount(context);
            Object array = Array.newInstance(getRawClass().getComponentType(), arraySize);
            context.incrementColumnIndex();
            arrayReader.readValue(typeReader, context, array);
            context.incrementColumnIndex();
            return array;
        }
        context.incrementColumnIndex(getNumFields() + 1);
        return null;
    }

    public static class Arrays {
        public static class ArrayReaders {
            public abstract static interface ArrayReader<T> {
                void readValue(TypeReader typeReader, ReadContext context, T newArray);
            }

            public static class BoolArrayReader implements ArrayReader<boolean[]> {
                @Override
                public void readValue(TypeReader typeReader, ReadContext context, boolean[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = typeReader.readBoolean(context);
                    }
                }
            }

            public static class CharArrayReader implements ArrayReader<char[]> {
                @Override
                public void readValue(TypeReader typeReader, ReadContext context, char[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = typeReader.readChar(context);
                    }
                }
            }

            public static class ShortArrayReader implements ArrayReader<short[]> {
                @Override
                public void readValue(TypeReader typeReader, ReadContext context, short[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = typeReader.readShort(context);
                    }
                }
            }

            public static class IntArrayReader implements ArrayReader<int[]> {
                @Override
                public void readValue(TypeReader typeReader, ReadContext context, int[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = typeReader.readInt(context);
                    }
                }
            }

            public static class LongArrayReader implements ArrayReader<long[]> {
                @Override
                public void readValue(TypeReader typeReader, ReadContext context, long[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = typeReader.readLong(context);
                    }
                }
            }

            public static class FloatArrayReader implements ArrayReader<float[]> {
                @Override
                public void readValue(TypeReader typeReader, ReadContext context, float[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = typeReader.readFloat(context);
                    }
                }
            }

            public static class DoubleArrayReader implements ArrayReader<double[]> {
                @Override
                public void readValue(TypeReader typeReader, ReadContext context, double[] newArray) {
                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = typeReader.readDouble(context);
                    }
                }
            }
        }

        public static class ArrayWriters {
            public static interface ArrayWriter<T> {
                void writeArray(T value, TypeWriter typeWriter, WriteContext context);
            }

            public static class BoolArrayWriter implements ArrayWriter<boolean[]> {
                @Override
                public void writeArray(boolean[] value, TypeWriter typeWriter, WriteContext context) {
                    for (boolean b : value) {
                        typeWriter.writeBool(b, context);
                    }
                }
            }

            public static class ShortArrayWriter implements ArrayWriter<short[]> {
                @Override
                public void writeArray(short[] value, TypeWriter typeWriter, WriteContext context) {
                    for (short s : value) {
                        typeWriter.writeShort(s, context);
                    }
                }
            }

            public static class CharArrayWriter implements ArrayWriter<char[]> {
                @Override
                public void writeArray(char[] value, TypeWriter typeWriter, WriteContext context) {
                    for (char c : value) {
                        typeWriter.writeChar(c, context);
                    }
                }
            }

            public static class IntArrayWriter implements ArrayWriter<int[]> {
                @Override
                public void writeArray(int[] value, TypeWriter typeWriter, WriteContext context) {
                    for (int i : value) {
                        typeWriter.writeInt(i, context);
                    }
                }
            }

            public static class LongArrayWriter implements ArrayWriter<long[]> {
                @Override
                public void writeArray(long[] value, TypeWriter typeWriter, WriteContext context) {
                    for (long l : value) {
                        typeWriter.writeLong(l, context);
                    }
                }
            }

            public static class FloatArrayWriter implements ArrayWriter<float[]> {
                @Override
                public void writeArray(float[] value, TypeWriter typeWriter, WriteContext context) {
                    for (float f : value) {
                        typeWriter.writeFloat(f, context);
                    }
                }
            }

            public static class DoubleArrayWriter implements ArrayWriter<double[]> {
                @Override
                public void writeArray(double[] value, TypeWriter typeWriter, WriteContext context) {
                    for (double d : value) {
                        typeWriter.writeDouble(d, context);
                    }
                }
            }
        }
    }

    private class RefArrayWriter implements Arrays.ArrayWriters.ArrayWriter<Object[]> {
        @Override
        public void writeArray(Object[] value, TypeWriter typeWriter, WriteContext context) {
            int index = context.getColumnIndex();
            for (Object o : value) {
                contentSerializer.writeValue(o, typeWriter, context);
                context.setColumnIndex(index); //rewind back to start of component type
            }
        }
    }

    private class RefArrayReader implements Arrays.ArrayReaders.ArrayReader<Object[]> {
        @Override
        public void readValue(TypeReader typeReader, ReadContext context, Object[] newArray) {
            int index = context.getColumnIndex();
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = contentSerializer.readValue(typeReader, context);
                context.setColumnIndex(index);
            }
        }
    }
}