package com.github.jasonruckman.sidney.ext.guava;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

public class Unsigned {
  public static class UnsignedIntegerSerializer extends Serializer<UnsignedInteger> {
    private Primitives.LongSerializer serializer;

    @Override
    public void consume(TypeRef typeRef, SerializerContext context) {
      serializer = context.longSerializer();
    }

    @Override
    public void writeValue(UnsignedInteger value, WriteContext context) {
      serializer.writeLong(value.longValue(), context);
    }

    @Override
    public UnsignedInteger readValue(ReadContext context) {
      return UnsignedInteger.valueOf(serializer.readLong(context));
    }
  }

  public static class UnsignedLongSerializer extends Serializer<UnsignedLong> {
    private Primitives.LongSerializer serializer;

    @Override
    public void consume(TypeRef typeRef, SerializerContext context) {
      serializer = context.longSerializer();
    }

    @Override
    public void writeValue(UnsignedLong value, WriteContext context) {
      serializer.writeLong(value.longValue(), context);
    }

    @Override
    public UnsignedLong readValue(ReadContext context) {
      return UnsignedLong.valueOf(serializer.readLong(context));
    }
  }
}
