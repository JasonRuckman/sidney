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
package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

import java.util.Map;

public class MapSerializer<T extends Map> extends Serializer<T> {
  private Serializer keySerializer;
  private Serializer valueSerializer;
  private InstanceFactoryCache cache = new InstanceFactoryCache();

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    keySerializer = context.serializer(typeRef.getTypeParameters().get(0), this);
    valueSerializer = context.serializer(typeRef.getTypeParameters().get(1), this);

    addNumFieldsToIncrementBy(keySerializer.getNumFieldsToIncrementBy());
    addNumFieldsToIncrementBy(valueSerializer.getNumFieldsToIncrementBy());
  }

  @Override
  public void writeValue(T value, WriteContext context) {
    writeMap(value, context);
  }

  @Override
  public T readValue(ReadContext context) {
    return readMap(context);
  }

  @Override
  public boolean requiresTypeColumn() {
    return true;
  }

  private void writeMap(T map, WriteContext context) {
    if (context.shouldWriteValue(map)) {
      context.writeConcreteType(map.getClass());
      context.writeRepetitionCount(map.size());
      //bump index forward to key
      context.incrementColumnIndex();
      int valueIndex = context.getColumnIndex();
      for (Object e : map.entrySet()) {
        context.setColumnIndex(valueIndex); //for each entry, make sure we are starting at the root
        Map.Entry entry = (Map.Entry) e;
        keySerializer.writeValue(entry.getKey(), context);
        valueSerializer.writeValue(entry.getValue(), context);
      }
    } else {
      context.incrementColumnIndex(getNumFieldsToIncrementBy());
    }
  }

  private T readMap(ReadContext context) {
    if (context.shouldReadValue()) {
      T map = (T) cache.newInstance(context.readConcreteType());
      int size = context.readRepetitionCount();
      context.incrementColumnIndex();
      int idx = context.getColumnIndex();
      for (int i = 0; i < size; i++) {
        context.setColumnIndex(idx);
        map.put(keySerializer.readValue(context), valueSerializer.readValue(context));
      }
      return map;
    }
    context.incrementColumnIndex(getNumFieldsToIncrementBy());
    return null;
  }
}