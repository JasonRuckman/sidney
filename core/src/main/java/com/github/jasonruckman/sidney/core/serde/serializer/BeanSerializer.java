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
import com.github.jasonruckman.sidney.core.serde.InstanceFactory;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Default serializer for non-primitive types that aren't consumed by another type
 */
public class BeanSerializer<T> extends Serializer<T> {
  private InstanceFactory<T> instanceFactory;
  private List<Serializer> fieldSerializers = new ArrayList<>();

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    for (TypeRef.TypeFieldRef fieldRef : typeRef.getFields()) {
      Serializer fieldSerializer = context.serializer(fieldRef, this);
      fieldSerializers.add(fieldSerializer);
    }
    instanceFactory = getFactories().get(typeRef.getType());
  }

  @Override
  public void writeValue(T value, WriteContext context) {
    writeBean(value, context);
  }

  @Override
  public T readValue(ReadContext context) {
    return readBean(context);
  }

  private void writeBean(T value, WriteContext context) {
    for (Serializer serializer : fieldSerializers) {
      serializer.writeFromField(value, context);
    }
  }

  private T readBean(ReadContext context) {
    T bean = instanceFactory.newInstance();
    for (Serializer handler : fieldSerializers) {
      handler.readIntoField(bean, context);
    }
    return bean;
  }
}