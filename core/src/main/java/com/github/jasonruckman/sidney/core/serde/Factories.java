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