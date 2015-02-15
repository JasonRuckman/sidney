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

import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.serde.InstanceFactory;
import com.github.jasonruckman.sidney.core.serde.Type;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Various configurations for serialization
 */
public class SidneyConf {
  public static final int DEFAULT_SIDNEY_PAGE_SIZE = 1024;
  private Registrations registrations = new Registrations();
  private boolean useUnsafe = true;
  private boolean referenceTrackingEnabled = false;
  private int pageSize = DEFAULT_SIDNEY_PAGE_SIZE;
  private Map<Type, Encoding> defaultEncodings = new HashMap<>();

  private SidneyConf() {

  }

  /**
   * Create a new configuration
   */
  public static SidneyConf newConf() {
    return new SidneyConf();
  }

  /**
   * Create a new configuration with given registrations
   */
  public static SidneyConf newConf(Registrations registrations) {
    SidneyConf c = new SidneyConf();
    c.registrations = registrations;
    return c;
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
  public SidneyConf useUnsafe(boolean useUnsafe) {
    this.useUnsafe = useUnsafe;
    return this;
  }

  /**
   * Set page size that sidney will use to break up large numbers of objects
   */
  public SidneyConf pageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Register a {@link com.github.jasonruckman.sidney.core.serde.serializer.Serializer} that will handle instances of the given type, but not subclasses
   */
  public <T, R extends Serializer<T>> SidneyConf register(Class<T> type, Class<R> serializerClass) {
    registrations.register(type, serializerClass);
    return this;
  }

  public <T> SidneyConf register(Class<T> type, InstanceFactory<T> factory) {
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