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

public class Shorts {
  public static class RandomShortGenerator extends Generator<Short> {
    private final Random random = new Random(11L);

    @Override
    public Short next() {
      return (short) random.nextInt(Short.MAX_VALUE);
    }
  }

  public static class AlwaysShortGenerator extends Generator<Short> {
    private short value;

    public AlwaysShortGenerator(short value) {
      this.value = value;
    }

    @Override
    public Short next() {
      return value;
    }
  }

  public static class IncrementingShortGeneratingStrategy extends Generator<Short> {
    private short current = 0;
    private Supplier.ShortSupplier incrementSupplier;

    public IncrementingShortGeneratingStrategy(Supplier.ShortSupplier incrementSupplier) {
      this.incrementSupplier = incrementSupplier;
    }

    @Override
    public Short next() {
      current += incrementSupplier.apply();
      return current;
    }
  }

  public static class RangeShortGenerator extends Generator<Short> {
    private final short min;
    private final short max;
    private final RandomDataGenerator randomData = new RandomDataGenerator();

    public RangeShortGenerator(short min, short max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public Short next() {
      return (short) randomData.nextInt(min, max);
    }
  }
}