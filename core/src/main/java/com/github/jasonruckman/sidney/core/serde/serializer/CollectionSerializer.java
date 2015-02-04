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

import java.util.Collection;

/**
 * Serializes non-map collection types
 */
public class CollectionSerializer extends Serializer<Collection> {
  private Serializer contentSerializer;
  private InstanceFactoryCache cache = new InstanceFactoryCache();

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    contentSerializer = context.serializer(typeRef.getTypeParameters().get(0), this);
    addNumFieldsToIncrementBy(contentSerializer.getNumFieldsToIncrementBy());
  }

  @Override
  public void writeValue(Object value, WriteContext context) {
    writeCollection((Collection) value, context);
  }

  @Override
  public Object readValue(ReadContext context) {
    return readCollection(context);
  }

  @Override
  public boolean requiresTypeColumn() {
    return true;
  }

  private void writeCollection(Collection collection, WriteContext context) {
    if (context.writeNullMarkerAndType(collection)) {
      context.incrementColumnIndex();
      int index = context.getColumnIndex();
      context.writeRepetitionCount(collection.size());
      for (Object value : collection) {
        contentSerializer.writeValue(value, context);
        context.setColumnIndex(index); //rewind back to the start of the component type
      }
      context.incrementColumnIndex();
    } else {
      context.incrementColumnIndex(getNumFieldsToIncrementBy() + 1);
    }
  }

  private Collection readCollection(ReadContext context) {
    if (context.readNullMarker()) {
      Collection c = (Collection) cache.newInstance(context.readConcreteType());
      context.incrementColumnIndex();
      int count = context.readRepetitionCount();
      int valueIndex = context.getColumnIndex();
      for (int i = 0; i < count; i++) {
        context.setColumnIndex(valueIndex);
        c.add(contentSerializer.readValue(context));
      }
      context.incrementColumnIndex();
      return c;
    }
    context.incrementColumnIndex(getNumFieldsToIncrementBy() + 1);
    return null;
  }
}