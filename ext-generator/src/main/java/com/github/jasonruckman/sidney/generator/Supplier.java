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

public interface Supplier<T> {
  T apply();

  public static interface BoolSupplier {
    boolean apply();
  }

  public static interface ByteSupplier {
    byte apply();
  }

  public static interface CharSupplier {
    char apply();
  }

  public static interface ShortSupplier {
    short apply();
  }

  public static interface IntSupplier {
    int apply();
  }

  public static interface LongSupplier {
    long apply();
  }

  public static interface FloatSupplier {
    float apply();
  }

  public static interface DoubleSupplier {
    double apply();
  }
}