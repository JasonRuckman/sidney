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

import com.github.jasonruckman.sidney.core.Configuration;
import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;

public interface SerializerContext {
  /**
   * Create a new serializer given a {@link com.github.jasonruckman.sidney.core.type.TypeRef}
   *
   * @param typeRef the type ref
   * @param <T>     type of serializer
   * @return an initialized serializer
   */
  <T> Serializer<T> serializer(TypeRef typeRef);

  /**
   * Create a new serializer given a {@link com.github.jasonruckman.sidney.core.type.TypeRef} with the given {@link Serializer} as a parent.
   * Unless you are the root serializer, this is the call to use for custom serialization
   *
   * @param typeRef the type ref
   * @param parent  type of serializer
   * @param <T>     type of serializer
   * @return an initialized serializer with the given {@link Serializer} as the parent.
   */
  <T> Serializer<T> serializer(TypeRef typeRef, Serializer parent);

  Primitives.BooleanSerializer boolSerializer();

  Primitives.BooleanSerializer boolSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Boolean> boolRefSerializer();

  Serializer<Boolean> boolRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Primitives.ByteSerializer byteSerializer();

  Primitives.ByteSerializer byteSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Byte> byteRefSerializer();

  Serializer<Byte> byteRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Primitives.CharSerializer charSerializer();

  Primitives.CharSerializer charSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Character> charRefSerializer();

  Serializer<Character> charRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Primitives.ShortSerializer shortSerializer();

  Primitives.ShortSerializer shortSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Short> shortRefSerializer();

  Serializer<Short> shortRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Primitives.IntSerializer intSerializer();

  Primitives.IntSerializer intSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Integer> intRefSerializer();

  Serializer<Integer> intRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Primitives.LongSerializer longSerializer();

  Primitives.LongSerializer longSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Long> longRefSerializer();

  Serializer<Long> longRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Primitives.FloatSerializer floatSerializer();

  Primitives.FloatSerializer floatSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Float> floatRefSerializer();

  Serializer<Float> floatRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Primitives.DoubleSerializer doubleSerializer();

  Primitives.DoubleSerializer doubleSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<Double> doubleRefSerializer();

  Serializer<Double> doubleRefSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<String> stringSerializer();

  Serializer<String> stringSerializer(TypeRef.TypeFieldRef fieldRef);

  Serializer<byte[]> binarySerializer();

  Serializer<byte[]> binarySerializer(TypeRef.TypeFieldRef fieldRef);

  Configuration conf();
}