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
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;

import java.util.BitSet;

public class BitSetSerializer extends Serializer<BitSet> {
  private Primitives.BooleanSerializer bitsSerializer;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    bitsSerializer = context.boolSerializer();
  }

  @Override
  public void writeValue(BitSet value, WriteContext context) {
    int length = value.length();
    context.getMeta().writeRepetitionCount(length);
    for(int i = 0; i < length; i++) {
      bitsSerializer.writeBoolean(value.get(i), context);
    }
  }

  @Override
  public BitSet readValue(ReadContext context) {
    BitSet bitSet = new BitSet();
    int length = context.getMeta().readRepetitionCount();
    for(int i = 0; i < length; i++) {
      if(bitsSerializer.readBoolean(context)) {
        bitSet.set(i);
      }
    }
    return bitSet;
  }
}
