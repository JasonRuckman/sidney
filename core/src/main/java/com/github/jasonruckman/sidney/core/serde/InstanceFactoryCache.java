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

/**
 * Simple cache for creating new instances that remembers the previous type created for quick lookup
 */
public class InstanceFactoryCache {
  private Class lastClass;
  private InstanceFactory lastFactory;
  private Factories factories;

  public InstanceFactoryCache(Factories factories) {
    this.factories = factories;
  }

  /**
   * Create a new instance of {@param type}, must have a default constructor
   */
  public <T> T newInstance(Class<T> type) {
    if (type == lastClass) {
      return (T) lastFactory.newInstance();
    }

    lastFactory = factories.get(type);
    lastClass = type;

    return (T) lastFactory.newInstance();
  }
}