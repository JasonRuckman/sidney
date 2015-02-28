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
import com.github.jasonruckman.sidney.core.serde.factory.InstanceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Default serializer for non-primitive types that aren't consumed by another type
 */
public class BeanSerializer<T> extends Serializer<T> {
  private InstanceFactory<T> instanceFactory;
  private Serializer[] fieldSerializers;

  @Override
  public void initialize(TypeRef typeRef, SerializerContext context) {
    fieldSerializers = new Serializer[typeRef.getFields().size()];
    for(int i = 0; i < fieldSerializers.length; i++) {
      fieldSerializers[i] = context.serializer(typeRef.getFields().get(i), this);
    }
    instanceFactory = getFactories().get(typeRef.getType());
  }

  @Override
  public void writeValue(T value, Contexts.WriteContext context) {
    writeBean(value, context);
  }

  @Override
  public T readValue(Contexts.ReadContext context) {
    return readBean(context);
  }

  private void writeBean(T value, Contexts.WriteContext context) {
    for (Serializer serializer : fieldSerializers) {
      serializer.writeFromField(value, context);
    }
  }

  private T readBean(Contexts.ReadContext context) {
    T bean = instanceFactory.newInstance();
    for (Serializer handler : fieldSerializers) {
      handler.readIntoField(bean, context);
    }
    return bean;
  }
}