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

import com.github.jasonruckman.sidney.core.serde.factory.DefaultInstanceFactory;
import com.github.jasonruckman.sidney.core.serde.factory.InstanceFactory;

import java.util.*;

public class MapGenerator<K, V> extends Generator<Map<K, V>> {
  private Generator<Integer> sizeGenerator;
  private Generator<K> keyGenerator;
  private Generator<V> valueGenerator;
  private InstanceFactory<HashMap<K, V>> hashMapFactory = (InstanceFactory) new DefaultInstanceFactory<>(HashMap.class);
  private InstanceFactory<TreeMap<K, V>> treeMapFactory = (InstanceFactory) new DefaultInstanceFactory<>(TreeMap.class);

  public MapGenerator(Generator<Integer> sizeGenerator, Generator<K> keyGenerator, Generator<V> valueGenerator) {
    this.sizeGenerator = sizeGenerator;
    this.keyGenerator = keyGenerator;
    this.valueGenerator = valueGenerator;
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
    Map<K, V> map = hashMapFactory.newInstance();
    for (int i = 0; i < sizeGenerator.next(); i++) {
      map.put(keyGenerator.next(), valueGenerator.next());
    }
    return map;
  }

  public HashMap<K, V> nextHashMap() {
    HashMap<K, V> map = hashMapFactory.newInstance();
    for (int i = 0; i < sizeGenerator.next(); i++) {
      map.put(keyGenerator.next(), valueGenerator.next());
    }
    return map;
  }

  public TreeMap<K, V> nextTreeMap() {
    TreeMap<K, V> map = treeMapFactory.newInstance();
    for (int i = 0; i < sizeGenerator.next(); i++) {
      map.put(keyGenerator.next(), valueGenerator.next());
    }
    return map;
  }

  public List<HashMap<K, V>> listHashMaps(int count) {
    List<HashMap<K, V>> list = new ArrayList<>();
    for(int i = 0; i < count; i++) {
      list.add(nextHashMap());
    }
    return list;
  }
}