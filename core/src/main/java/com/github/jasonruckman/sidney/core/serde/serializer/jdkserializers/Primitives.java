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
package com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.Type;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.PrimitiveSerializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;

public class Primitives {
  public static class BooleanSerializer extends BoolRefSerializer {
    public final boolean readBoolean(ReadContext context) {
      context.setColumnIndex(startIndex());
      boolean value = context.readBoolean();
      return value;
    }

    public final void writeBoolean(boolean value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeBool(value);
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeBoolean(getAccessor().getBoolean(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setBoolean(parent, readBoolean(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class BoolRefSerializer extends PrimitiveSerializer<Boolean> {
    @Override
    public void writeValue(Boolean value, WriteContext context) {
      context.writeBool(value);
    }

    @Override
    public Boolean readValue(ReadContext context) {
      return context.readBoolean();
    }

    @Override
    public Type getType() {
      return Type.BOOLEAN;
    }

  }

  public static class ByteArraySerializer extends PrimitiveSerializer<byte[]> {
    @Override
    public void writeValue(byte[] value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeBytes(value);
    }

    @Override
    public byte[] readValue(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readBytes();
    }

    @Override
    public Type getType() {
      return Type.BINARY;
    }
  }

  public static class ByteRefSerializer extends PrimitiveSerializer<Byte> {
    @Override
    public void writeValue(Byte value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeByte(value);
    }

    @Override
    public Byte readValue(ReadContext context) {
      context.setColumnIndex(startIndex());
      byte b = context.readByte();
      return b;
    }

    @Override
    public Type getType() {
      return Type.INT32;
    }

  }

  public static class ByteSerializer extends ByteRefSerializer {
    public final byte readByte(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readByte();
    }

    public final void writeByte(byte value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeByte(value);
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeByte(getAccessor().getByte(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setByte(parent, readByte(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class CharRefSerializer extends PrimitiveSerializer<Character> {
    @Override
    public void writeValue(Character value, WriteContext context) {
      context.writeChar(value);
    }

    @Override
    public Character readValue(ReadContext context) {
      return context.readChar();
    }

    @Override
    public Type getType() {
      return Type.INT32;
    }

  }

  public static class CharSerializer extends CharRefSerializer {
    public final void writeChar(char value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeChar(value);
    }

    public final char readChar(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readChar();
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeChar(getAccessor().getChar(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setChar(parent, readChar(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class DoubleRefSerializer extends PrimitiveSerializer<Double> {
    @Override
    public void writeValue(Double value, WriteContext context) {
      context.writeDouble(value);
    }

    @Override
    public Double readValue(ReadContext context) {
      return context.readDouble();
    }

    @Override
    public Type getType() {
      return Type.FLOAT64;
    }

  }

  public static class DoubleSerializer extends DoubleRefSerializer {
    public final double readDouble(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readDouble();
    }

    public void writeDouble(double value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeDouble(value);
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeDouble(getAccessor().getDouble(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setDouble(parent, readDouble(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class EnumSerializer<T extends Enum> extends PrimitiveSerializer<T> {
    private T[] values;

    @Override
    public void consume(TypeRef typeRef, SerializerContext context) {
      values = (T[]) typeRef.getType().getEnumConstants();
    }

    @Override
    public void writeValue(T value, WriteContext context) {
      context.writeInt(value.ordinal());
    }

    @Override
    public T readValue(ReadContext context) {
      return values[context.readInt()];
    }

    @Override
    public Type getType() {
      return Type.ENUM;
    }
  }

  public static class FloatRefSerializer extends PrimitiveSerializer<Float> {
    @Override
    public void writeValue(Float value, WriteContext context) {
      context.writeFloat(value);
    }

    @Override
    public Float readValue(ReadContext context) {
      return context.readFloat();
    }

    @Override
    public Type getType() {
      return Type.FLOAT32;
    }

  }

  public static class FloatSerializer extends FloatRefSerializer {
    public final void writeFloat(float value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeFloat(value);
    }

    public final float readFloat(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readFloat();
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeFloat(getAccessor().getFloat(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setFloat(parent, readFloat(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class LongRefSerializer extends PrimitiveSerializer<Long> {
    @Override
    public void writeValue(Long value, WriteContext context) {
      context.writeLong(value);
    }

    @Override
    public Long readValue(ReadContext context) {
      return context.readLong();
    }

    @Override
    public Type getType() {
      return Type.INT64;
    }

  }

  public static class LongSerializer extends LongRefSerializer {
    public final void writeLong(long value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeLong(value);
    }

    public final long readLong(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readLong();
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeLong(getAccessor().getLong(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setLong(parent, readLong(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class StringSerializer extends PrimitiveSerializer<String> {
    @Override
    public void writeValue(String value, WriteContext context) {
      context.writeString(value);
    }

    @Override
    public String readValue(ReadContext context) {
      return context.readString();
    }

    @Override
    public Type getType() {
      return Type.STRING;
    }
  }

  public static class ShortRefSerializer extends PrimitiveSerializer<Short> {
    @Override
    public Type getType() {
      return Type.INT32;
    }

    @Override
    public void writeValue(Short value, WriteContext context) {
      context.writeShort(value);
    }

    @Override
    public Short readValue(ReadContext context) {
      return context.readShort();
    }
  }

  public static class ShortSerializer extends ShortRefSerializer {
    public final void writeShort(short value, WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeShort(value);
    }

    public final short readShort(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readShort();
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeShort(getAccessor().getShort(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setShort(parent, readShort(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class IntRefSerializer extends PrimitiveSerializer<Integer> {
    @Override
    public void writeValue(Integer value, WriteContext context) {
      context.writeInt(value);
    }

    @Override
    public Integer readValue(ReadContext context) {
      return context.readInt();
    }

    @Override
    public Type getType() {
      return Type.INT32;
    }

  }

  public static class IntSerializer extends IntRefSerializer {
    public final void writeInt(int value, WriteContext writeContext) {
      writeContext.setColumnIndex(startIndex());
      writeContext.writeInt(value);
    }

    public final int readInt(ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readInt();
    }

    @Override
    public void writeFromField(Object parent, WriteContext context) {
      writeInt(getAccessor().getInt(parent), context);
    }

    @Override
    public void readIntoField(Object parent, ReadContext context) {
      getAccessor().setInt(parent, readInt(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }
}