package com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;

import java.util.BitSet;

public class BitSetSerializer extends Serializer<BitSet> {
  private Primitives.IntSerializer sizeSerializer;
  private Primitives.BooleanSerializer bitsSerializer;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    sizeSerializer = context.intSerializer();
    bitsSerializer = context.boolSerializer();
  }

  @Override
  public void writeValue(BitSet value, WriteContext context) {
    int length = value.length();
    sizeSerializer.writeInt(length, context);
    for(int i = 0; i < length; i++) {
      bitsSerializer.writeBoolean(value.get(i), context);
    }
  }

  @Override
  public BitSet readValue(ReadContext context) {
    BitSet bitSet = new BitSet();
    int length = sizeSerializer.readInt(context);
    for(int i = 0; i < length; i++) {
      if(bitsSerializer.readBoolean(context)) {
        bitSet.set(i);
      }
    }
    return bitSet;
  }
}
