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

public class Bools {
  public static class RandomBooleanGenerator extends Generator<Boolean> {
    private final Random random = new Random(11L);

    @Override
    public Boolean next() {
      return random.nextBoolean();
    }
  }

  public static class PercentageTrueGenerator extends Generator<Boolean> {
    private final Random random = new Random(11L);
    private double percentage;

    public PercentageTrueGenerator(double percentage) {
      this.percentage = percentage;
    }

    @Override
    public Boolean next() {
      return (random.nextDouble() <= percentage);
    }
  }

  public static class AlwaysFalseBooleanGenerator extends Generator<Boolean> {
    @Override
    public Boolean next() {
      return false;
    }
  }

  public static class AlwaysTrueBooleanGenerator extends Generator<Boolean> {
    @Override
    public Boolean next() {
      return true;
    }
  }
}