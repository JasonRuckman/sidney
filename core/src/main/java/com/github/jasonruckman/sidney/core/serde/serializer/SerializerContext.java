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

public interface SerializerContext {
  /**
   * Create a new serializer given a {@link com.github.jasonruckman.sidney.core.TypeRef}
   *
   * @param typeRef the type ref
   * @param <T>     type of serializer
   * @return an initialized serializer
   */
  <T> Serializer<T> serializer(TypeRef typeRef);

  /**
   * Create a new serializer given a {@link com.github.jasonruckman.sidney.core.TypeRef} with the given {@link Serializer} as a parent.
   * Unless you are the root serializer, this is the call to use for custom serialization
   *
   * @param typeRef the type ref
   * @param parent  type of serializer
   * @param <T>     type of serializer
   * @return an initialized serializer with the given {@link Serializer} as the parent.
   */
  <T> Serializer<T> serializer(TypeRef typeRef, Serializer parent);
}