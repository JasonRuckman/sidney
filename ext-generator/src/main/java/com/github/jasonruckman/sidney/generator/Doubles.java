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

public class Doubles {
  public static class RandomDoubleGenerator extends Generator<Double> {
    private final Random random = new Random(11L);

    @Override
    public Double next() {
      return random.nextDouble();
    }
  }

  public static class AlwaysDoubleGenerator extends Generator<Double> {
    private double value;

    public AlwaysDoubleGenerator(double value) {
      this.value = value;
    }

    @Override
    public Double next() {
      return value;
    }
  }

  public static class IncrementingDoubleGeneratingStrategy extends Generator<Double> {
    private double current = 0;
    private Supplier.DoubleSupplier incrementSupplier;

    public IncrementingDoubleGeneratingStrategy(Supplier.DoubleSupplier incrementSupplier) {
      this.incrementSupplier = incrementSupplier;
    }

    @Override
    public Double next() {
      current += incrementSupplier.apply();
      return current;
    }
  }

  public static class RangeDoubleGenerator extends Generator<Double> {
    private final double min;
    private final double max;
    private final Random randomData = new Random();

    public RangeDoubleGenerator(double min, double max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public Double next() {
      return randomData.nextDouble() * (max - min) + min;
    }
  }
}