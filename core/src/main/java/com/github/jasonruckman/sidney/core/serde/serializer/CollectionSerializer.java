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
import com.github.jasonruckman.sidney.core.serde.InstanceFactoryCache;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

import java.util.Collection;

/**
 * Serializes non-map collection types
 */
public class CollectionSerializer<V, T extends Collection<V>> extends Serializer<T> {
  private Serializer<V> contentSerializer;
  private InstanceFactoryCache cache;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    contentSerializer = context.serializer(typeRef.getTypeParameters().get(0), this);
    cache = new InstanceFactoryCache(getFactories());
  }

  @Override
  public void writeValue(T value, WriteContext context) {
    writeCollection(value, context);
  }

  @Override
  public T readValue(ReadContext context) {
    return readCollection(context);
  }

  @Override
  public boolean requiresTypeColumn() {
    return true;
  }

  private void writeCollection(Collection<V> collection, WriteContext context) {
    context.getMeta().writeConcreteType(collection.getClass());
    context.getMeta().writeRepetitionCount(collection.size());

    for (V value : collection) {
      contentSerializer.writeValue(value, context);
    }
  }

  private T readCollection(ReadContext context) {
    T c = (T) cache.newInstance(context.getMeta().readConcreteType());
    int count = context.getMeta().readRepetitionCount();
    for (int i = 0; i < count; i++) {
      c.add(contentSerializer.readValue(context));
    }
    return c;
  }
}