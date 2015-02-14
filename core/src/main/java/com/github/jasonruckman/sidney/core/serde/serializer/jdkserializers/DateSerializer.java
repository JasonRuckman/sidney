package com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;

import java.util.Date;

/**
 * Serializes {@link java.util.Date} objects
 */
public class DateSerializer extends Serializer<Date> {
  private Primitives.LongSerializer longSerializer;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    longSerializer = context.longSerializer();
  }

  @Override
  public void writeValue(Date value, WriteContext context) {
    longSerializer.writeLong(value.getTime(), context);
  }

  @Override
  public Date readValue(ReadContext context) {
    return new Date(longSerializer.readLong(context));
  }
}