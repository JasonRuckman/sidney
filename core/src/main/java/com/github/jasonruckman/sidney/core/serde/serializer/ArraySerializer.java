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
package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.serde.Contexts;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Serializes all array types, primitive and reference type.
 */
public class ArraySerializer extends Serializer<Object> {
  private final Map<Class, ArrayWriter> PRIMITIVE_WRITERS = new HashMap<>();
  private final Map<Class, ArrayReader> PRIMITIVE_READERS = new HashMap<>();
  private Serializer contentSerializer;
  private ArrayWriter arrayWriter;
  private ArrayReader arrayReader;
  private Class<?> rawClass;

  public ArraySerializer() {
    PRIMITIVE_WRITERS.put(boolean[].class, new BoolArrayWriter());
    PRIMITIVE_WRITERS.put(char[].class, new CharArrayWriter());
    PRIMITIVE_WRITERS.put(short[].class, new ShortArrayWriter());
    PRIMITIVE_WRITERS.put(int[].class, new IntArrayWriter());
    PRIMITIVE_WRITERS.put(long[].class, new LongArrayWriter());
    PRIMITIVE_WRITERS.put(float[].class, new FloatArrayWriter());
    PRIMITIVE_WRITERS.put(double[].class, new DoubleArrayWriter());

    PRIMITIVE_READERS.put(boolean[].class, new BoolArrayReader());
    PRIMITIVE_READERS.put(char[].class, new CharArrayReader());
    PRIMITIVE_READERS.put(short[].class, new ShortArrayReader());
    PRIMITIVE_READERS.put(int[].class, new IntArrayReader());
    PRIMITIVE_READERS.put(long[].class, new LongArrayReader());
    PRIMITIVE_READERS.put(float[].class, new FloatArrayReader());
    PRIMITIVE_READERS.put(double[].class, new DoubleArrayReader());
  }

  @Override
  public void initialize(TypeRef typeRef, SerializerContext context) {
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
  public void writeValue(Object value, Contexts.WriteContext context) {
    writeArray(value, context);
  }

  @Override
  public Object readValue(Contexts.ReadContext context) {
    return readArray(context);
  }

  private void writeArray(Object array, Contexts.WriteContext context) {
    context.getMeta().writeRepetitionCount(Array.getLength(array));
    arrayWriter.writeArray(array, context);
  }

  private Object readArray(Contexts.ReadContext context) {
    Object array = Array.newInstance(rawClass.getComponentType(), context.getMeta().readRepetitionCount());
    arrayReader.readValue(context, array);
    return array;
  }

  public abstract interface ArrayReader<T> {
    void readValue(Contexts.ReadContext context, T newArray);
  }


  interface ArrayWriter<T> {
    void writeArray(T value, Contexts.WriteContext context);
  }

  class BoolArrayReader implements ArrayReader<boolean[]> {
    @Override
    public void readValue(Contexts.ReadContext context, boolean[] newArray) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = context.readBoolean();
      }
    }
  }

  class CharArrayReader implements ArrayReader<char[]> {
    @Override
    public void readValue(Contexts.ReadContext context, char[] newArray) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = context.readChar();
      }
    }
  }

  class ShortArrayReader implements ArrayReader<short[]> {
    @Override
    public void readValue(Contexts.ReadContext context, short[] newArray) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = context.readShort();
      }
    }
  }

  class IntArrayReader implements ArrayReader<int[]> {
    @Override
    public void readValue(Contexts.ReadContext context, int[] newArray) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = context.readInt();
      }
    }
  }

  class LongArrayReader implements ArrayReader<long[]> {
    @Override
    public void readValue(Contexts.ReadContext context, long[] newArray) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = context.readLong();
      }
    }
  }

  class FloatArrayReader implements ArrayReader<float[]> {
    @Override
    public void readValue(Contexts.ReadContext context, float[] newArray) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = context.readFloat();
      }
    }
  }

  class DoubleArrayReader implements ArrayReader<double[]> {
    @Override
    public void readValue(Contexts.ReadContext context, double[] newArray) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = context.readDouble();
      }
    }
  }

  class BoolArrayWriter implements ArrayWriter<boolean[]> {
    @Override
    public void writeArray(boolean[] value, Contexts.WriteContext context) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (boolean b : value) {
        context.writeBool(b);
      }
    }
  }

  class ShortArrayWriter implements ArrayWriter<short[]> {
    @Override
    public void writeArray(short[] value, Contexts.WriteContext context) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (short s : value) {
        context.writeShort(s);
      }
    }
  }

  class CharArrayWriter implements ArrayWriter<char[]> {
    @Override
    public void writeArray(char[] value, Contexts.WriteContext context) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (char c : value) {
        context.writeChar(c);
      }
    }
  }

  class IntArrayWriter implements ArrayWriter<int[]> {
    @Override
    public void writeArray(int[] value, Contexts.WriteContext context) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (int i : value) {
        context.writeInt(i);
      }
    }
  }

  class LongArrayWriter implements ArrayWriter<long[]> {
    @Override
    public void writeArray(long[] value, Contexts.WriteContext context) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (long l : value) {
        context.writeLong(l);
      }
    }
  }

  class FloatArrayWriter implements ArrayWriter<float[]> {
    @Override
    public void writeArray(float[] value, Contexts.WriteContext context) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (float f : value) {
        context.writeFloat(f);
      }
    }
  }

  class DoubleArrayWriter implements ArrayWriter<double[]> {
    @Override
    public void writeArray(double[] value, Contexts.WriteContext context) {
      context.setColumnIndex(contentSerializer.startIndex());
      for (double d : value) {
        context.writeDouble(d);
      }
    }
  }


  private class RefArrayWriter implements ArrayWriter<Object[]> {
    @Override
    public void writeArray(Object[] value, Contexts.WriteContext context) {
      for (Object o : value) {
        contentSerializer.writeValue(o, context);
      }
    }
  }

  private class RefArrayReader implements ArrayReader<Object[]> {
    @Override
    public void readValue(Contexts.ReadContext context, Object[] newArray) {
      for (int i = 0; i < newArray.length; i++) {
        newArray[i] = contentSerializer.readValue(context);
      }
    }
  }
}