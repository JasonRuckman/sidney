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

import java.util.Random;

public class Nulls {
  public static class AlwaysNullGenerator<T> extends NullableGenerator<T> {
    @Override
    public T next() {
      return null;
    }

    @Override
    public Generator<T> wrap(Generator<T> strategy) {
      return this;
    }
  }

  public static class PercentageNullGenerator<T> extends NullableGenerator<T> {
    private final Random random = new Random(11L);
    private Generator<T> source;
    private double percentage;

    public PercentageNullGenerator(double percentage) {
      this.percentage = percentage;
    }

    @Override
    public T next() {
      if (random.nextDouble() <= percentage) {
        return source.next();
      }
      return null;
    }

    @Override
    public Generator<T> wrap(Generator<T> strategy) {
      source = strategy;
      return this;
    }
  }

  public abstract static class NullableGenerator<T> extends Generator<T> {
    public abstract T next();

    public abstract Generator<T> wrap(Generator<T> strategy);
  }
}