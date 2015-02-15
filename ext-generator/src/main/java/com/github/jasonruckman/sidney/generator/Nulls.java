package com.github.jasonruckman.sidney.generator;

import java.util.Random;

public class Nulls {
  public static class AlwaysNullGenerator<T> extends NullableGenerator<T> {
    @Override
    public T next() {
      return null;
    }

    @Override
    public Generator<T> wrap(Generator<T> strategy) {
      return this;
    }
  }

  public static class PercentageNullGenerator<T> extends NullableGenerator<T> {
    private final Random random = new Random(11L);
    private Generator<T> source;
    private double percentage;

    public PercentageNullGenerator(double percentage) {
      this.percentage = percentage;
    }

    @Override
    public T next() {
      if (random.nextDouble() <= percentage) {
        return source.next();
      }
      return null;
    }

    @Override
    public Generator<T> wrap(Generator<T> strategy) {
      source = strategy;
      return this;
    }
  }

  public abstract static class NullableGenerator<T> extends Generator<T> {
    public abstract T next();

    public abstract Generator<T> wrap(Generator<T> strategy);
  }
}