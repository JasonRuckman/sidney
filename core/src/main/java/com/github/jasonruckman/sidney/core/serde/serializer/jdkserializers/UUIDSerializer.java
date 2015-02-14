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