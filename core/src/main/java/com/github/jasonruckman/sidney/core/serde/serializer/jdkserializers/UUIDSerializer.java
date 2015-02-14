package com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;

import java.util.UUID;

public class UUIDSerializer extends Serializer<UUID> {
  private Primitives.LongSerializer mostSig;
  private Primitives.LongSerializer leastSig;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    mostSig = context.longSerializer();
    leastSig = context.longSerializer();
  }

  @Override
  public void writeValue(UUID value, WriteContext context) {
    mostSig.writeLong(value.getMostSignificantBits(), context);
    leastSig.writeLong(value.getLeastSignificantBits(), context);
  }

  @Override
  public UUID readValue(ReadContext context) {
    return new UUID(mostSig.readLong(context), leastSig.readLong(context));
  }
}