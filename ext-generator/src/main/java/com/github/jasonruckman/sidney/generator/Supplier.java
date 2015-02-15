package com.github.jasonruckman.sidney.generator;

public interface Supplier<T> {
  T apply();

  public static interface BoolSupplier {
    boolean apply();
  }

  public static interface ByteSupplier {
    byte apply();
  }

  public static interface CharSupplier {
    char apply();
  }

  public static interface ShortSupplier {
    short apply();
  }

  public static interface IntSupplier {
    int apply();
  }

  public static interface LongSupplier {
    long apply();
  }

  public static interface FloatSupplier {
    float apply();
  }

  public static interface DoubleSupplier {
    double apply();
  }
}