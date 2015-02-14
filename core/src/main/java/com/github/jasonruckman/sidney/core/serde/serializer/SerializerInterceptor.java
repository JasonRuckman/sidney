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
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

public class SerializerInterceptor<T> extends Serializer<T> {
  private final Serializer<T> delegate;

  public SerializerInterceptor(Serializer<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    delegate.consume(typeRef, context);
  }

  @Override
  public void writeValue(T value, WriteContext context) {
    context.setColumnIndex(delegate.startIndex());
    if (context.shouldWriteValue(value)) {
      delegate.writeValue(value, context);
    }
  }

  @Override
  public T readValue(ReadContext context) {
    context.setColumnIndex(delegate.startIndex());
    T value;
    if (context.shouldReadValue()) {
      value = delegate.readValue(context);
    } else {
      value = null;
    }
    return value;
  }

  @Override
  protected Accessors.FieldAccessor getAccessor() {
    return delegate.getAccessor();
  }

  @Override
  public void setAccessor(Accessors.FieldAccessor accessor) {
    delegate.setAccessor(accessor);
  }
}