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

public class Bytes {
  public static class RandomByteGenerator extends Generator<Byte> {
    private final Random random = new Random(11L);

    @Override
    public Byte next() {
      return (byte) random.nextInt(255);
    }
  }

  public static class AlwaysByteGenerator extends Generator<Byte> {
    private byte value;

    public AlwaysByteGenerator(byte value) {
      this.value = value;
    }

    @Override
    public Byte next() {
      return value;
    }
  }

  public static class RangeByteGenerator extends Generator<Byte> {
    private final Random random = new Random(11L);
    private final byte min;
    private final byte max;

    public RangeByteGenerator(byte min, byte max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public Byte next() {
      return (byte) (random.nextInt((max - min) + 1) + min);
    }
  }
}