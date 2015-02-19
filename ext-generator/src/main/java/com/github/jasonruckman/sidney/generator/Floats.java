package com.github.jasonruckman.sidney.generator;

import java.util.Random;

public class Floats {
  public static class RandomFloatGenerator extends Generator<Float> {
    private final Random random = new Random(11L);

    @Override
    public Float next() {
      return random.nextFloat();
    }
  }

  public static class AlwaysFloatGenerator extends Generator<Float> {
    private float value;

    public AlwaysFloatGenerator(float value) {
      this.value = value;
    }

    @Override
    public Float next() {
      return value;
    }
  }

  public static class IncrementingFloatGeneratingStrategy extends Generator<Float> {
    private float current = 0;
    private Supplier.FloatSupplier incrementSupplier;

    public IncrementingFloatGeneratingStrategy(Supplier.FloatSupplier incrementSupplier) {
      this.incrementSupplier = incrementSupplier;
    }

    @Override
    public Float next() {
      current += incrementSupplier.apply();
      return current;
    }
  }

  public static class RangeFloatGenerator extends Generator<Float> {
    private final float min;
    private final float max;
    private final Random randomData = new Random();

    public RangeFloatGenerator(float min, float max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public Float next() {
      return randomData.nextFloat() * (max - min) + min;
    }
  }
}