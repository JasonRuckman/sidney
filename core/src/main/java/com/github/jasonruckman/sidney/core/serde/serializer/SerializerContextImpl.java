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
import com.github.jasonruckman.sidney.core.SidneyConf;
import com.github.jasonruckman.sidney.core.SidneyException;
import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.Factories;
import com.github.jasonruckman.sidney.core.serde.References;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.BitSetSerializer;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.DateSerializer;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.UUIDSerializer;

import java.util.*;

public class SerializerContextImpl implements SerializerContext {
  private final List<SerializerEntry> entries = new ArrayList<>();
  private final List<Serializer> serializers = new ArrayList<>();
  private SidneyConf.Registrations registrations;
  private SidneyConf conf;
  private References references;
  private Factories factories;
  private int counter = 0;

  public SerializerContextImpl(SidneyConf conf, References references) {
    this.conf = conf;
    this.references = references;
    registrations = conf.getRegistrations();
    factories = new Factories(conf);
    addCustomFactories();
    addPrimitiveFactories();
    addArrayFactories();
    addJdkDefaults();

    addEntry(Collection.class, CollectionSerializer.class);
    addEntry(Map.class, MapSerializer.class);
    addEntry(Object.class, BeanSerializer.class);
  }

  private void addCustomFactories() {
    entries.addAll(registrations.getRegistrations());
  }

  private void addJdkDefaults() {
    addEntry(Date.class, DateSerializer.class);
    addEntry(UUID.class, UUIDSerializer.class);
    addEntry(BitSet.class, BitSetSerializer.class);
  }

  private void addPrimitiveFactories() {
    addEntry(boolean.class, Primitives.BooleanSerializer.class);
    addEntry(Boolean.class, Primitives.BoolRefSerializer.class);
    addEntry(byte.class, Primitives.ByteSerializer.class);
    addEntry(Byte.class, Primitives.ByteRefSerializer.class);
    addEntry(char.class, Primitives.CharSerializer.class);
    addEntry(Character.class, Primitives.CharRefSerializer.class);
    addEntry(short.class, Primitives.ShortSerializer.class);
    addEntry(Short.class, Primitives.ShortRefSerializer.class);
    addEntry(int.class, Primitives.IntSerializer.class);
    addEntry(Integer.class, Primitives.IntRefSerializer.class);
    addEntry(long.class, Primitives.LongSerializer.class);
    addEntry(Long.class, Primitives.LongRefSerializer.class);
    addEntry(float.class, Primitives.FloatSerializer.class);
    addEntry(Float.class, Primitives.FloatRefSerializer.class);
    addEntry(double.class, Primitives.DoubleSerializer.class);
    addEntry(Double.class, Primitives.DoubleRefSerializer.class);
    addEntry(Enum.class, Primitives.EnumSerializer.class);
    addEntry(String.class, Primitives.StringSerializer.class);
    addEntry(byte[].class, Primitives.ByteArraySerializer.class);
  }

  private void addArrayFactories() {
    Class<ArraySerializer> arraySerializerClass = ArraySerializer.class;

    addEntry(boolean[].class, arraySerializerClass);
    addEntry(char[].class, arraySerializerClass);
    addEntry(short[].class, arraySerializerClass);
    addEntry(int[].class, arraySerializerClass);
    addEntry(long[].class, arraySerializerClass);
    addEntry(float[].class, arraySerializerClass);
    addEntry(double[].class, arraySerializerClass);
    addEntry(Object[].class, arraySerializerClass);
  }

  public <T> Serializer<T> serializer(TypeRef typeRef) {
    return serializer(typeRef, null);
  }

  public <T> Serializer<T> serializer(TypeRef typeRef, Serializer parent) {
    Class<T> clazz = (Class<T>) typeRef.getType();
    Serializer<T> serializer = null;
    for (SerializerEntry entry : entries) {
      if (entry.getType() == clazz || entry.getType().isAssignableFrom(clazz)) {
        try {
          serializer = (Serializer<T>) entry.getSerializerType().getConstructor().newInstance();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        break;
      }
    }
    if (serializer == null) {
      throw new SidneyException(String.format("Could not resolve serializer for type: %s", clazz));
    }
    serializers.add(serializer);

    if (typeRef instanceof TypeRef.TypeFieldRef) {
      serializer.setAccessor(Accessors.newAccessor(conf,
          ((TypeRef.TypeFieldRef) typeRef).getJdkField()
      ));
    }

    serializer.setStartIndex(counter++);
    serializer.setFactories(factories);
    serializer.consume(typeRef, this);

    if (PrimitiveSerializer.class.isAssignableFrom(serializer.getClass())) {
      if (((PrimitiveSerializer) serializer).intercept()) {
        if (conf.isReferenceTrackingEnabled()) {
          serializer = new ReferenceTrackingSerializerInterceptor<>(serializer, references);
        } else {
          serializer = new SerializerInterceptor<>(serializer);
        }
      }
    } else {
      if (conf.isReferenceTrackingEnabled()) {
        serializer = new ReferenceTrackingSerializerInterceptor<>(serializer, references);
      } else {
        serializer = new SerializerInterceptor<>(serializer);
      }
    }

    return serializer;
  }

  @Override
  public Primitives.BooleanSerializer boolSerializer() {
    return (Primitives.BooleanSerializer) (Serializer) serializer(TypeRef.makeRef(boolean.class));
  }

  @Override
  public Primitives.BooleanSerializer boolSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.BooleanSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Boolean> boolRefSerializer() {
    return serializer(TypeRef.makeRef(Boolean.class));
  }

  @Override
  public Serializer<Boolean> boolRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Primitives.ByteSerializer byteSerializer() {
    return (Primitives.ByteSerializer) (Serializer) serializer(TypeRef.makeRef(byte.class));
  }

  @Override
  public Primitives.ByteSerializer byteSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.ByteSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Byte> byteRefSerializer() {
    return serializer(TypeRef.makeRef(Byte.class));
  }

  @Override
  public Serializer<Byte> byteRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Primitives.CharSerializer charSerializer() {
    return (Primitives.CharSerializer) (Serializer) serializer(TypeRef.makeRef(char.class));
  }

  @Override
  public Primitives.CharSerializer charSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.CharSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Character> charRefSerializer() {
    return serializer(TypeRef.makeRef(Character.class));
  }

  @Override
  public Serializer<Character> charRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Primitives.ShortSerializer shortSerializer() {
    return (Primitives.ShortSerializer) (Serializer) serializer(TypeRef.makeRef(short.class));
  }

  @Override
  public Primitives.ShortSerializer shortSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.ShortSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Short> shortRefSerializer() {
    return serializer(TypeRef.makeRef(Short.class));
  }

  @Override
  public Serializer<Short> shortRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Primitives.IntSerializer intSerializer() {
    return (Primitives.IntSerializer) (Serializer) serializer(TypeRef.makeRef(int.class));
  }

  @Override
  public Primitives.IntSerializer intSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.IntSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Integer> intRefSerializer() {
    return serializer(TypeRef.makeRef(Integer.class));
  }

  @Override
  public Serializer<Integer> intRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Primitives.LongSerializer longSerializer() {
    return (Primitives.LongSerializer) (Serializer) serializer(TypeRef.makeRef(long.class));
  }

  @Override
  public Primitives.LongSerializer longSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.LongSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Long> longRefSerializer() {
    return serializer(TypeRef.makeRef(Long.class));
  }

  @Override
  public Serializer<Long> longRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Primitives.FloatSerializer floatSerializer() {
    return (Primitives.FloatSerializer) (Serializer) serializer(TypeRef.makeRef(float.class));
  }

  @Override
  public Primitives.FloatSerializer floatSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.FloatSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Float> floatRefSerializer() {
    return serializer(TypeRef.makeRef(Float.class));
  }

  @Override
  public Serializer<Float> floatRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Primitives.DoubleSerializer doubleSerializer() {
    return (Primitives.DoubleSerializer) (Serializer) serializer(TypeRef.makeRef(double.class));
  }

  @Override
  public Primitives.DoubleSerializer doubleSerializer(TypeRef.TypeFieldRef fieldRef) {
    return (Primitives.DoubleSerializer) (Serializer) serializer(fieldRef);
  }

  @Override
  public Serializer<Double> doubleRefSerializer() {
    return serializer(TypeRef.makeRef(Double.class));
  }

  @Override
  public Serializer<Double> doubleRefSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Serializer<String> stringSerializer() {
    return serializer(TypeRef.makeRef(String.class));
  }

  @Override
  public Serializer<String> stringSerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public Serializer<byte[]> binarySerializer() {
    return serializer(TypeRef.makeRef(byte[].class));
  }

  @Override
  public Serializer<byte[]> binarySerializer(TypeRef.TypeFieldRef fieldRef) {
    return serializer(fieldRef);
  }

  @Override
  public SidneyConf conf() {
    return conf;
  }

  public void finish(SerializerFinalizer consumer) {
    for (Serializer serializer : serializers) {
      consumer.finish(serializer);
    }
  }

  private void addEntry(Class type, Class<? extends Serializer> serializerType) {
    entries.add(new SerializerEntry(type, serializerType));
  }
}