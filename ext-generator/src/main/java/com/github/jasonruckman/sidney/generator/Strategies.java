package com.github.jasonruckman.sidney.generator;

import java.util.Collection;

public class Strategies {
  public static Generator<Boolean> randomBool() {
    return new Bools.RandomBooleanGenerator();
  }

  public static Generator<Boolean> percentageTrue(double percentage) {
    return new Bools.PercentageTrueGenerator(percentage);
  }

  public static Generator<Boolean> allFalse() {
    return new Bools.AlwaysFalseBooleanGenerator();
  }

  public static Generator<Boolean> allTrue() {
    return new Bools.AlwaysTrueBooleanGenerator();
  }

  public static Generator<Byte> randomByte() {
    return new Bytes.RandomByteGenerator();
  }

  public static Generator<Byte> always(byte value) {
    return new Bytes.AlwaysByteGenerator(value);
  }

  public static Generator<Byte> range(byte min, byte max) {
    return new Bytes.RangeByteGenerator(min, max);
  }

  public static Generator<Short> randomShort() {
    return new Shorts.RandomShortGenerator();
  }

  public static Generator<Short> always(short value) {
    return new Shorts.AlwaysShortGenerator(value);
  }

  public static Generator<Short> range(short min, short max) {
    return new Shorts.RangeShortGenerator(min, max);
  }

  public static Generator<Short> incrementingBy(final short value) {
    return new Shorts.IncrementingShortGeneratingStrategy(new Supplier.ShortSupplier() {
      @Override
      public short apply() {
        return value;
      }
    });
  }

  public static Generator<Short> incrementingBy(Supplier.ShortSupplier supplier) {
    return new Shorts.IncrementingShortGeneratingStrategy(supplier);
  }

  public static Generator<Character> randomChar() {
    return new Chars.RandomCharGenerator();
  }

  public static Generator<Character> always(char value) {
    return new Chars.AlwaysCharGenerator(value);
  }

  public static Generator<Character> range(char min, char max) {
    return new Chars.RangeCharGenerator(min, max);
  }

  public static Generator<Integer> randomInt() {
    return new Ints.RandomIntGenerator();
  }

  public static Generator<Integer> always(int value) {
    return new Ints.AlwaysIntGenerator(value);
  }

  public static Generator<Integer> range(int min, int max) {
    return new Ints.RangeIntGenerator(min, max);
  }

  public static Generator<Integer> incrementingBy(final int value) {
    return new Ints.IncrementingIntGeneratingStrategy(new Supplier.IntSupplier() {
      @Override
      public int apply() {
        return value;
      }
    });
  }

  public static Generator<Integer> incrementingBy(Supplier.IntSupplier supplier) {
    return new Ints.IncrementingIntGeneratingStrategy(supplier);
  }

  public static Generator<Long> randomLong() {
    return new Longs.RandomLongGenerator();
  }

  public static Generator<Long> always(long value) {
    return new Longs.AlwaysLongGenerator(value);
  }

  public static Generator<Long> range(long min, long max) {
    return new Longs.RangeLongGenerator(min, max);
  }

  public static Generator<Long> incrementingBy(final long value) {
    return new Longs.IncrementingLongGeneratingStrategy(new Supplier.LongSupplier() {
      @Override
      public long apply() {
        return value;
      }
    });
  }

  public static Generator<Long> incrementingBy(Supplier.LongSupplier supplier) {
    return new Longs.IncrementingLongGeneratingStrategy(supplier);
  }

  public static <R> Nulls.AlwaysNullGenerator<R> allNulls() {
    return new Nulls.AlwaysNullGenerator<>();
  }

  public static Nulls.PercentageNullGenerator percentageNull(double percentage) {
    return new Nulls.PercentageNullGenerator<>(percentage);
  }

  public static Generator<Float> randomFloat() {
    return new Floats.RandomFloatGenerator();
  }

  public static Generator<Float> always(float value) {
    return new Floats.AlwaysFloatGenerator(value);
  }

  public static Generator<Float> incrementingBy(final float value) {
    return new Floats.IncrementingFloatGeneratingStrategy(new Supplier.FloatSupplier() {
      @Override
      public float apply() {
        return value;
      }
    });
  }

  public static Generator<Float> incrementingBy(Supplier.FloatSupplier supplier) {
    return new Floats.IncrementingFloatGeneratingStrategy(supplier);
  }

  public static Generator<Double> randomDouble() {
    return new Doubles.RandomDoubleGenerator();
  }

  public static Generator<Double> always(double value) {
    return new Doubles.AlwaysDoubleGenerator(value);
  }

  public static Generator<Double> range(double min, double max) {
    return new Doubles.RangeDoubleGenerator(min, max);
  }

  public static Generator<Double> incrementingBy(final double value) {
    return new Doubles.IncrementingDoubleGeneratingStrategy(new Supplier.DoubleSupplier() {
      @Override
      public double apply() {
        return value;
      }
    });
  }

  public static Generator<Double> incrementingBy(Supplier.DoubleSupplier supplier) {
    return new Doubles.IncrementingDoubleGeneratingStrategy(supplier);
  }

  public static Generator<String> randomString() {
    return new Strings.RandomStringGenerator();
  }

  public static Generator<String> fromCorpus(Collection<String> corpus) {
    return new Strings.FromCorpusStringGenerator(corpus);
  }
}