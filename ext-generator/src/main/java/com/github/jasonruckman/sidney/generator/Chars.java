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