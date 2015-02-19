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

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.Random;

public class Ints {
  public static class RandomIntGenerator extends Generator<Integer> {
    private final Random random = new Random(11L);

    @Override
    public Integer next() {
      return random.nextInt();
    }
  }

  public static class AlwaysIntGenerator extends Generator<Integer> {
    private int value;

    public AlwaysIntGenerator(int value) {
      this.value = value;
    }

    @Override
    public Integer next() {
      return value;
    }
  }

  public static class IncrementingIntGeneratingStrategy extends Generator<Integer> {
    private int current = 0;
    private Supplier.IntSupplier incrementSupplier;

    public IncrementingIntGeneratingStrategy(Supplier.IntSupplier incrementSupplier) {
      this.incrementSupplier = incrementSupplier;
    }

    @Override
    public Integer next() {
      current += incrementSupplier.apply();
      return current;
    }
  }

  public static class RangeIntGenerator extends Generator<Integer> {
    private final int min;
    private final int max;
    private final RandomDataGenerator randomData = new RandomDataGenerator();

    public RangeIntGenerator(int min, int max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public Integer next() {
      return randomData.nextInt(min, max);
    }
  }
}