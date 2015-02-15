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