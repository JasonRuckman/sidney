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
package com.github.jasonruckman.sidney.core;

import com.github.jasonruckman.sidney.core.io.*;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.input.UnsafeInput;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.output.UnsafeOutput;
import com.github.jasonruckman.sidney.core.serde.factory.InstanceFactory;
import com.github.jasonruckman.sidney.core.type.Type;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Various configurations for serialization
 */
public class Configuration {
  public static final int DEFAULT_SIDNEY_PAGE_SIZE = 1024;
  private Map<String, String> configurations = new HashMap<>();
  private Registrations registrations = new Registrations();
  private boolean useUnsafe = true;
  private boolean referenceTrackingEnabled = false;
  private int pageSize = DEFAULT_SIDNEY_PAGE_SIZE;
  private Map<Type, Encoding> defaultEncodings = new HashMap<>();
  private IOType type = IOType.Default;

  private Configuration() {

  }

  /**
   * Create a new configuration
   */
  public static Configuration newConf() {
    return new Configuration();
  }

  /**
   * Create a new configuration with given registrations
   */
  public static Configuration newConf(Registrations registrations) {
    Configuration c = new Configuration();
    c.registrations = registrations;
    return c;
  }

  public void setType(IOType type) {
    this.type = type;
  }

  public boolean isReferenceTrackingEnabled() {
    return referenceTrackingEnabled;
  }

  public void setReferenceTrackingEnabled(boolean referenceTrackingEnabled) {
    this.referenceTrackingEnabled = referenceTrackingEnabled;
  }

  public boolean isUseUnsafe() {
    return useUnsafe;
  }

  public int getPageSize() {
    return pageSize;
  }

  /**
   * Set whether or not to use unsafe accessors when accessing fields. Defaults to true
   */
  public Configuration useUnsafe(boolean useUnsafe) {
    this.useUnsafe = useUnsafe;
    return this;
  }

  /**
   * Set page size that sidney will use to break up large numbers of objects
   */
  public Configuration pageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Register a {@link com.github.jasonruckman.sidney.core.serde.serializer.Serializer} that will handle instances of the given type, but not subclasses
   */
  public <T, R extends Serializer<T>> Configuration register(Class<T> type, Class<R> serializerClass) {
    registrations.register(type, serializerClass);
    return this;
  }

  public <T> Configuration register(Class<T> type, InstanceFactory<T> factory) {
    registrations.registerFactory(type, factory);
    return this;
  }

  public Encoding defaultEncoding(Type type) {
    return defaultEncodings.get(type);
  }

  public void overrideDefaultEncoding(Type type, Encoding encoding) {
    defaultEncodings.put(type, encoding);
  }

  public Registrations getRegistrations() {
    return registrations;
  }

  public Input newInput() {
    switch (type) {
      case Unsafe: return new UnsafeInput();
      default: return new Input();
    }
  }

  public Output newOutput() {
    switch (type) {
      case Unsafe: return new UnsafeOutput();
      default: return new Output();
    }
  }

  public static enum IOType {
    Default,
    Unsafe,
    UnsafeMemory
  }

  /**
   * Contains custom serializer implementations
   */
  public static class Registrations {
    private List<SerializerEntry> registeredFactories = new ArrayList<>();
    private Map<Class, InstanceFactory> instanceFactories = new HashMap<>();

    public Map<Class, InstanceFactory> getInstanceFactories() {
      return instanceFactories;
    }

    public List<SerializerEntry> getRegistrations() {
      return registeredFactories;
    }

    public <T, R extends Serializer<T>> void register(Class<T> type, Class<R> serializerFactory) {
      registeredFactories.add(
          new SerializerEntry(type, serializerFactory)
      );
    }

    public <T> void registerFactory(Class<T> type, InstanceFactory<T> factory) {
      instanceFactories.put(type, factory);
    }
  }
}