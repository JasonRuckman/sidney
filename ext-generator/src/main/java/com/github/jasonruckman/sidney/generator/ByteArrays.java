package com.github.jasonruckman.sidney.generator;

import java.util.Random;

public class ByteArrays {
  public static interface ByteArrayGenerator {
    byte[] next();
  }

  public static class RandomByteArrayGenerator extends Generator implements ByteArrayGenerator {
    private final Random random = new Random(11L);

    @Override
    public byte[] next() {
      byte[] bytes = new byte[random.nextInt(128)];
      random.nextBytes(bytes);
      return bytes;
    }
  }
}