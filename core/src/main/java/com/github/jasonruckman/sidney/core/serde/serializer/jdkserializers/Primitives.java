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

import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.serde.Contexts;
import com.github.jasonruckman.sidney.core.type.Type;
import com.github.jasonruckman.sidney.core.serde.serializer.PrimitiveSerializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;

public class Primitives {
  public static class BooleanSerializer extends BoolRefSerializer {
    public final boolean readBoolean(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      boolean value = context.readBoolean();
      return value;
    }

    public final void writeBoolean(boolean value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeBool(value);
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeBoolean(getAccessor().getBoolean(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
      getAccessor().setBoolean(parent, readBoolean(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class BoolRefSerializer extends PrimitiveSerializer<Boolean> {
    @Override
    public void writeValue(Boolean value, Contexts.WriteContext context) {
      context.writeBool(value);
    }

    @Override
    public Boolean readValue(Contexts.ReadContext context) {
      return context.readBoolean();
    }

    @Override
    public Type getType() {
      return Type.BOOLEAN;
    }

  }

  public static class ByteArraySerializer extends PrimitiveSerializer<byte[]> {
    @Override
    public void writeValue(byte[] value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeBytes(value);
    }

    @Override
    public byte[] readValue(Contexts.ReadContext context) {
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
    public void writeValue(Byte value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeByte(value);
    }

    @Override
    public Byte readValue(Contexts.ReadContext context) {
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
    public final byte readByte(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readByte();
    }

    public final void writeByte(byte value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeByte(value);
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeByte(getAccessor().getByte(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
      getAccessor().setByte(parent, readByte(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class CharRefSerializer extends PrimitiveSerializer<Character> {
    @Override
    public void writeValue(Character value, Contexts.WriteContext context) {
      context.writeChar(value);
    }

    @Override
    public Character readValue(Contexts.ReadContext context) {
      return context.readChar();
    }

    @Override
    public Type getType() {
      return Type.CHAR;
    }

  }

  public static class CharSerializer extends CharRefSerializer {
    public final void writeChar(char value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeChar(value);
    }

    public final char readChar(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readChar();
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeChar(getAccessor().getChar(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
      getAccessor().setChar(parent, readChar(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class DoubleRefSerializer extends PrimitiveSerializer<Double> {
    @Override
    public void writeValue(Double value, Contexts.WriteContext context) {
      context.writeDouble(value);
    }

    @Override
    public Double readValue(Contexts.ReadContext context) {
      return context.readDouble();
    }

    @Override
    public Type getType() {
      return Type.FLOAT64;
    }

  }

  public static class DoubleSerializer extends DoubleRefSerializer {
    public final double readDouble(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readDouble();
    }

    public void writeDouble(double value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeDouble(value);
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeDouble(getAccessor().getDouble(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
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
    public void initialize(TypeRef typeRef, SerializerContext context) {
      values = (T[]) typeRef.getType().getEnumConstants();
    }

    @Override
    public void writeValue(T value, Contexts.WriteContext context) {
      context.writeInt(value.ordinal());
    }

    @Override
    public T readValue(Contexts.ReadContext context) {
      return values[context.readInt()];
    }

    @Override
    public Type getType() {
      return Type.ENUM;
    }
  }

  public static class FloatRefSerializer extends PrimitiveSerializer<Float> {
    @Override
    public void writeValue(Float value, Contexts.WriteContext context) {
      context.writeFloat(value);
    }

    @Override
    public Float readValue(Contexts.ReadContext context) {
      return context.readFloat();
    }

    @Override
    public Type getType() {
      return Type.FLOAT32;
    }

  }

  public static class FloatSerializer extends FloatRefSerializer {
    public final void writeFloat(float value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeFloat(value);
    }

    public final float readFloat(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readFloat();
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeFloat(getAccessor().getFloat(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
      getAccessor().setFloat(parent, readFloat(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class LongRefSerializer extends PrimitiveSerializer<Long> {
    @Override
    public void writeValue(Long value, Contexts.WriteContext context) {
      context.writeLong(value);
    }

    @Override
    public Long readValue(Contexts.ReadContext context) {
      return context.readLong();
    }

    @Override
    public Type getType() {
      return Type.INT64;
    }

  }

  public static class LongSerializer extends LongRefSerializer {
    public final void writeLong(long value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeLong(value);
    }

    public final long readLong(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readLong();
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeLong(getAccessor().getLong(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
      getAccessor().setLong(parent, readLong(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class StringSerializer extends PrimitiveSerializer<String> {
    @Override
    public void writeValue(String value, Contexts.WriteContext context) {
      context.writeString(value);
    }

    @Override
    public String readValue(Contexts.ReadContext context) {
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
      return Type.INT16;
    }

    @Override
    public void writeValue(Short value, Contexts.WriteContext context) {
      context.writeShort(value);
    }

    @Override
    public Short readValue(Contexts.ReadContext context) {
      return context.readShort();
    }
  }

  public static class ShortSerializer extends ShortRefSerializer {
    public final void writeShort(short value, Contexts.WriteContext context) {
      context.setColumnIndex(startIndex());
      context.writeShort(value);
    }

    public final short readShort(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readShort();
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeShort(getAccessor().getShort(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
      getAccessor().setShort(parent, readShort(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }

  public static class IntRefSerializer extends PrimitiveSerializer<Integer> {
    @Override
    public void writeValue(Integer value, Contexts.WriteContext context) {
      context.writeInt(value);
    }

    @Override
    public Integer readValue(Contexts.ReadContext context) {
      return context.readInt();
    }

    @Override
    public Type getType() {
      return Type.INT32;
    }

  }

  public static class IntSerializer extends IntRefSerializer {
    public final void writeInt(int value, Contexts.WriteContext writeContext) {
      writeContext.setColumnIndex(startIndex());
      writeContext.writeInt(value);
    }

    public final int readInt(Contexts.ReadContext context) {
      context.setColumnIndex(startIndex());
      return context.readInt();
    }

    @Override
    public void writeFromField(Object parent, Contexts.WriteContext context) {
      writeInt(getAccessor().getInt(parent), context);
    }

    @Override
    public void readIntoField(Object parent, Contexts.ReadContext context) {
      getAccessor().setInt(parent, readInt(context));
    }

    @Override
    public boolean intercept() {
      return false;
    }
  }
}