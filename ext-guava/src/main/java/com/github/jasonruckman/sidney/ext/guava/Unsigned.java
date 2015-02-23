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
package com.github.jasonruckman.sidney.ext.guava;

import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.serde.Contexts;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

public class Unsigned {
  public static class UnsignedIntegerSerializer extends Serializer<UnsignedInteger> {
    private Primitives.LongSerializer serializer;

    @Override
    public void initialize(TypeRef typeRef, SerializerContext context) {
      serializer = context.longSerializer();
    }

    @Override
    public void writeValue(UnsignedInteger value, Contexts.WriteContext context) {
      serializer.writeLong(value.longValue(), context);
    }

    @Override
    public UnsignedInteger readValue(Contexts.ReadContext context) {
      return UnsignedInteger.valueOf(serializer.readLong(context));
    }
  }

  public static class UnsignedLongSerializer extends Serializer<UnsignedLong> {
    private Primitives.LongSerializer serializer;

    @Override
    public void initialize(TypeRef typeRef, SerializerContext context) {
      serializer = context.longSerializer();
    }

    @Override
    public void writeValue(UnsignedLong value, Contexts.WriteContext context) {
      serializer.writeLong(value.longValue(), context);
    }

    @Override
    public UnsignedLong readValue(Contexts.ReadContext context) {
      return UnsignedLong.valueOf(serializer.readLong(context));
    }
  }
}
