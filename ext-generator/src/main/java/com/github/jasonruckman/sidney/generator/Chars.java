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

public class Chars {
  public static class RandomCharGenerator extends Generator<Character> {
    private final Random random = new Random(11L);

    @Override
    public Character next() {
      return (char) random.nextInt(Character.MAX_VALUE);
    }
  }

  public static class RangeCharGenerator extends Generator<Character> {
    private final char min;
    private final char max;
    private final RandomDataGenerator randomData = new RandomDataGenerator();

    public RangeCharGenerator(char min, char max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public Character next() {
      return (char) randomData.nextInt(min, max);
    }
  }

  public static class AlwaysCharGenerator extends Generator<Character> {
    private final char value;

    public AlwaysCharGenerator(char value) {
      this.value = value;
    }

    @Override
    public Character next() {
      return value;
    }
  }
}