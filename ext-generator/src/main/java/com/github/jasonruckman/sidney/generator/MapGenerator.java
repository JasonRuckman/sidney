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
