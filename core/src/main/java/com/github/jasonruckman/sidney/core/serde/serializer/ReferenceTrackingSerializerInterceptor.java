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

import com.github.jasonruckman.sidney.core.Accessors;
import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.*;

/**
 * Intercepts non-primitives, keeps track of them and replaces references with values
 */
public class ReferenceTrackingSerializerInterceptor<T> extends Serializer<T> {
  private final Serializer<T> delegate;
  private final References references;

  public ReferenceTrackingSerializerInterceptor(Serializer<T> delegate, References references) {
    this.delegate = delegate;
    this.references = references;
  }

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    delegate.consume(typeRef, context);
  }

  @Override
  public void writeValue(T value, WriteContext context) {
    ReferenceTrackingWriteContext c = (ReferenceTrackingWriteContext) context;
    if (value != null) {
      c.writeNotNull();
      context.setColumnIndex(delegate.startIndex());
      int def = references.trackObject(value);
      if (def > 0) {
        //we wrote a reference, write the definition out and bump past this serializer
        c.writeDefinition(def);
      } else {
        c.writeDefinition(def);
        delegate.writeValue(value, context);
      }
    } else {
      c.writeNull();
    }
  }

  @Override
  public T readValue(ReadContext context) {
    ReferenceTrackingReadContext c = (ReferenceTrackingReadContext) context;
    boolean isNotNull = c.readNullMarker();

    if (!isNotNull) {
      return null;
    }

    context.setColumnIndex(delegate.startIndex());

    int definition = c.readDefinition();
    //the object value is in the high bits
    if (definition == 0) {
      int reference = references.nextCounter();
      T value = delegate.readValue(c);
      references.addReference(value, reference);
      return value;
    }
    return (T) references.getReference(definition);
  }

  @Override
  public boolean requiresTypeColumn() {
    return delegate.requiresTypeColumn();
  }

  @Override
  protected Accessors.FieldAccessor getAccessor() {
    return delegate.getAccessor();
  }
}