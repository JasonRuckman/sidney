package com.github.jasonruckman.sidney.ext.guava;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;
import com.google.common.collect.ImmutableList;

public class ImmutableListSerializer<T> extends Serializer<ImmutableList<T>> {
  private Primitives.IntSerializer sizeSerializer;
  private Serializer<T> contentSerializer;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    sizeSerializer = context.intSerializer();
    contentSerializer = context.serializer(typeRef.getTypeParameters().get(0));
  }

  @Override
  public void writeValue(ImmutableList<T> value, WriteContext context) {
    sizeSerializer.writeInt(value.size(), context);
    for(T o : value) {
      contentSerializer.writeValue(o, context);
    }
  }

  @Override
  public ImmutableList<T> readValue(ReadContext context) {
    int size = sizeSerializer.readInt(context);
    ImmutableList.Builder<T> builder = ImmutableList.builder();
    for(int i = 0; i < size; i++) {
      builder.add(contentSerializer.readValue(context));
    }
    return builder.build();
  }
}