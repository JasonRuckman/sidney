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
package com.github.jasonruckman.sidney.core.serde;

import com.github.jasonruckman.sidney.core.SidneyConf;
import com.github.jasonruckman.sidney.core.serde.DefaultInstanceFactory;
import com.github.jasonruckman.sidney.core.serde.InstanceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * All the registered and unregistered ways of creating instances for types
 */
public class Factories {
  private final SidneyConf conf;
  private final Map<Class, InstanceFactory> defaultFactories = new HashMap<>();

  public Factories(SidneyConf conf) {
    this.conf = conf;
  }

  /**
   * Get a instance factory for this type, if none is found {@link com.github.jasonruckman.sidney.core.serde.DefaultInstanceFactory}
   */
  public <T> InstanceFactory<T> get(Class<?> type) {
    InstanceFactory<T> factory = conf.getRegistrations().getInstanceFactories().get(type);
    if(factory == null) {
      factory = defaultFactories.get(type);
      if(factory == null) {
        factory = (InstanceFactory<T>) defaultFactory(type);
        defaultFactories.put(type, factory);
      }
    }
    return factory;
  }

  public static <R> InstanceFactory<R> defaultFactory(Class<R> type) {
    return new DefaultInstanceFactory<>(type);
  }
}