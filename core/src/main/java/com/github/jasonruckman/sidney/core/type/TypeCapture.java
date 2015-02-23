package com.github.jasonruckman.sidney.core.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeCapture<T> {
  /**
   * Returns the captured type.
   */
  protected final Type capture() {
    Type superclass = getClass().getGenericSuperclass();
    return ((ParameterizedType) superclass).getActualTypeArguments()[0];
  }
}
