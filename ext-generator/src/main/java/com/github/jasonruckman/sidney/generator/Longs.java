package com.github.jasonruckman.sidney.generator;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.Random;

public class Longs {
  public static class RandomLongGenerator extends Generator<Long> {
    private final Random random = new Random(11L);

    @Override
    public Long next() {
      return random.nextLong();
    }
  }

  public static class AlwaysLongGenerator extends Generator<Long> {
    private long value;

    public AlwaysLongGenerator(long value) {
      this.value = value;
    }

    @Override
    public Long next() {
      return value;
    }
  }

  public static class IncrementingLongGeneratingStrategy extends Generator<Long> {
    private long current = 0;
    private Supplier.LongSupplier incrementSupplier;

    public IncrementingLongGeneratingStrategy(Supplier.LongSupplier incrementSupplier) {
      this.incrementSupplier = incrementSupplier;
    }

    @Override
    public Long next() {
      current += incrementSupplier.apply();
      return current;
    }
  }

  public static class RangeLongGenerator extends Generator<Long> {
    private final long min;
    private final long max;
    private final RandomDataGenerator randomData = new RandomDataGenerator();

    public RangeLongGenerator(long min, long max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public Long next() {
      return randomData.nextLong(min, max);
    }
  }
}
