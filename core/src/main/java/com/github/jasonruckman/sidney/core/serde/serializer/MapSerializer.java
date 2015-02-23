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

import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.serde.Contexts;
import com.github.jasonruckman.sidney.core.serde.factory.InstanceFactoryCache;

import java.util.Map;

public class MapSerializer<K, V, T extends Map<K, V>> extends Serializer<T> {
  private Serializer<K> keySerializer;
  private Serializer<V> valueSerializer;
  private InstanceFactoryCache cache;

  @Override
  public void initialize(TypeRef typeRef, SerializerContext context) {
    keySerializer = context.serializer(typeRef.getTypeParameters().get(0), this);
    valueSerializer = context.serializer(typeRef.getTypeParameters().get(1), this);
    cache = new InstanceFactoryCache(getFactories());
  }

  @Override
  public void writeValue(T value, Contexts.WriteContext context) {
    writeMap(value, context);
  }

  @Override
  public T readValue(Contexts.ReadContext context) {
    return readMap(context);
  }

  @Override
  public boolean requiresTypeColumn() {
    return true;
  }

  private void writeMap(T map, Contexts.WriteContext context) {
    context.getMeta().writeConcreteType(map.getClass());
    context.getMeta().writeRepetitionCount(map.size());
    for (Map.Entry<K, V> e : map.entrySet()) {
      keySerializer.writeValue(e.getKey(), context);
      valueSerializer.writeValue(e.getValue(), context);
    }
  }

  private T readMap(Contexts.ReadContext context) {
    T map = (T) cache.newInstance(context.getMeta().readConcreteType());
    int size = context.getMeta().readRepetitionCount();
    for (int i = 0; i < size; i++) {
      map.put(keySerializer.readValue(context), valueSerializer.readValue(context));
    }
    return map;
  }
}