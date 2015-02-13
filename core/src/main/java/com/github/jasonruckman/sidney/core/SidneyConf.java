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

import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Various configurations for serialization
 */
public class SidneyConf {
  public static final int DEFAULT_SIDNEY_PAGE_SIZE = 1024;
  private Registrations registrations = new Registrations();
  private boolean useUnsafe = true;
  private boolean referenceTrackingEnabled = false;
  private int pageSize = DEFAULT_SIDNEY_PAGE_SIZE;

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
  public SidneyConf register(Class type, Class<? extends Serializer> serializerClass) {
    registrations.register(type, serializerClass);
    return this;
  }

  public Registrations getRegistrations() {
    return registrations;
  }

  /**
   * Contains custom serializer implementations
   */
  public static class Registrations {
    private List<SerializerEntry> registeredFactories = new ArrayList<>();

    public List<SerializerEntry> getRegistrations() {
      return registeredFactories;
    }

    public void register(Class type, Class<? extends Serializer> serializerFactory) {
      registeredFactories.add(
          new SerializerEntry(type, serializerFactory)
      );
    }
  }
}