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
package org.sidney.core.serde.handler;

import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

public class Arrays {
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