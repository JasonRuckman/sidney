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
package com.github.jasonruckman.sidney.generator;

import com.github.jasonruckman.sidney.core.serde.DefaultInstanceFactory;
import com.github.jasonruckman.sidney.core.serde.InstanceFactory;

import java.util.HashMap;
import java.util.Map;

public class MapGenerator<K, V> extends Generator<Map<K, V>> {
  private Generator<Integer> sizeGenerator;
  private Generator<K> keyGenerator;
  private Generator<V> valueGenerator;
  private InstanceFactory<Map<K, V>> factory = (InstanceFactory) new DefaultInstanceFactory<>(HashMap.class);

  public MapGenerator(Generator<Integer> sizeGenerator, Generator<K> keyGenerator, Generator<V> valueGenerator) {
    this.sizeGenerator = sizeGenerator;
    this.keyGenerator = keyGenerator;
    this.valueGenerator = valueGenerator;
  }

  public void setFactory(InstanceFactory<Map<K, V>> factory) {
    this.factory = factory;
  }

  public void setSizeGenerator(Generator<Integer> sizeGenerator) {
    this.sizeGenerator = sizeGenerator;
  }

  public void setKeyGenerator(Generator<K> keyGenerator) {
    this.keyGenerator = keyGenerator;
  }

  public void setValueGenerator(Generator<V> valueGenerator) {
    this.valueGenerator = valueGenerator;
  }

  @Override
  public Map<K, V> next() {
    Map<K, V> map = factory.newInstance();
    for (int i = 0; i < sizeGenerator.next(); i++) {
      map.put(keyGenerator.next(), valueGenerator.next());
    }
    return map;
  }
}
