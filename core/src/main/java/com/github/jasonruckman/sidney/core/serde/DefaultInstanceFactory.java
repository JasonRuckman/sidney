package com.github.jasonruckman.sidney.core.serde;

import com.esotericsoftware.reflectasm.ConstructorAccess;

public class DefaultInstanceFactory<T> implements InstanceFactory<T> {
  private ConstructorAccess<T> access;

  public DefaultInstanceFactory(Class<T> type) {
    access = ConstructorAccess.get(type);
  }

  /**
   * Create a new instance of the given type using the default constructor
   */
  @Override
  public T newInstance() {
    try {
      return access.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
