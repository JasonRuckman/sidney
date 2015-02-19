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