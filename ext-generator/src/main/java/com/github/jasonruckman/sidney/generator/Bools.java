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