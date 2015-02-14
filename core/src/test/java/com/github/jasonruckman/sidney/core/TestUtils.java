package com.github.jasonruckman.sidney.core;

import java.util.Random;

public class TestUtils {
  private static final Random random = new Random(11L);

  public static  <T> T maybeMakeNull(T value) {
    if (random.nextInt(10) < 4) {
      return null;
    }
    return value;
  }
}
