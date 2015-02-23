package com.github.jasonruckman.sidney.core.serde;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Hint {
  Class<?> value();
}