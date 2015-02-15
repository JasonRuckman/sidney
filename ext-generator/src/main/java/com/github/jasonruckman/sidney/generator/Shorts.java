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