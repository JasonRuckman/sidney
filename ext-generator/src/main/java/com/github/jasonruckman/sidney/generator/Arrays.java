/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jasonruckman.sidney.generator;

public class Arrays {
  public static Generator<boolean[]> bools(Generator<Integer> sizeGenerator, Generator<Boolean> valueGenerator) {
      return new BoolArrayGenerator(sizeGenerator, valueGenerator);
  }

  public static Generator<char[]> chars(Generator<Integer> sizeGenerator, Generator<Character> valueGenerator) {
    return new CharArrayGenerator(valueGenerator, sizeGenerator);
  }

  public static Generator<short[]> shorts(Generator<Integer> sizeGenerator, Generator<Short> valueGenerator) {
    return new ShortArrayGenerator(valueGenerator, sizeGenerator);
  }

  public static Generator<int[]> ints(Generator<Integer> sizeGenerator, Generator<Integer> valueGenerator) {
    return new IntArrayGenerator(valueGenerator, sizeGenerator);
  }

  public static Generator<long[]> longs(Generator<Integer> sizeGenerator, Generator<Long> valueGenerator) {
    return new LongArrayGenerator(sizeGenerator, valueGenerator);
  }

  public static Generator<float[]> floats(Generator<Integer> sizeGenerator, Generator<Float> valueGenerator) {
    return new FloatArrayGenerator(sizeGenerator, valueGenerator);
  }

  public static Generator<double[]> doubles(Generator<Integer> sizeGenerator, Generator<Double> valueGenerator) {
    return new DoubleArrayGenerator(sizeGenerator, valueGenerator);
  }

  public static class BoolArrayGenerator extends Generator<boolean[]> {
    private final Generator<Integer> sizeGenerator;
    private final Generator<Boolean> valueGenerator;

    public BoolArrayGenerator(Generator<Integer> sizeGenerator, Generator<Boolean> valueGenerator) {
      this.sizeGenerator = sizeGenerator;
      this.valueGenerator = valueGenerator;
    }

    @Override
    public boolean[] next() {
      int size = sizeGenerator.next();
      boolean[] booleans = new boolean[size];
      for(int i = 0; i < size; i++) {
        booleans[i] = valueGenerator.next();
      }
      return booleans;
    }
  }

  public static class CharArrayGenerator extends Generator<char[]> {
    private final Generator<Character> valueGenerator;
    private final Generator<Integer> sizeGenerator;

    public CharArrayGenerator(Generator<Character> valueGenerator, Generator<Integer> sizeGenerator) {
      this.valueGenerator = valueGenerator;
      this.sizeGenerator = sizeGenerator;
    }

    @Override
    public char[] next() {
      char[] chars = new char[sizeGenerator.next()];
      for(int i = 0; i < chars.length; i++) {
        chars[i] = valueGenerator.next();
      }
      return chars;
    }
  }

  public static class IntArrayGenerator extends Generator<int[]> {
    private final Generator<Integer> sizeGenerator;
    private final Generator<Integer> valueGenerator;

    public IntArrayGenerator(Generator<Integer> valueGenerator, Generator<Integer> sizeGenerator) {
      this.sizeGenerator = sizeGenerator;
      this.valueGenerator = valueGenerator;
    }

    @Override
    public int[] next() {
      int[] ints = new int[sizeGenerator.next()];
      for(int i = 0; i < ints.length; i++) {
        ints[i] = valueGenerator.next();
      }
      return ints;
    }
  }

  public static class ShortArrayGenerator extends Generator<short[]> {
    private final Generator<Short> valueGenerator;
    private final Generator<Integer> sizeGenerator;

    public ShortArrayGenerator(Generator<Short> valueGenerator, Generator<Integer> sizeGenerator) {
      this.valueGenerator = valueGenerator;
      this.sizeGenerator = sizeGenerator;
    }

    @Override
    public short[] next() {
      short[] shorts = new short[sizeGenerator.next()];
      for(int i = 0; i < shorts.length; i++) {
        shorts[i] = valueGenerator.next();
      }
      return shorts;
    }
  }

  public static class FloatArrayGenerator extends Generator<float[]> {
    private final Generator<Integer> sizeGenerator;
    private final Generator<Float> floatGenerator;

    public FloatArrayGenerator(Generator<Integer> sizeGenerator, Generator<Float> floatGenerator) {
      this.sizeGenerator = sizeGenerator;
      this.floatGenerator = floatGenerator;
    }

    @Override
    public float[] next() {
      float[] floats = new float[sizeGenerator.next()];
      for(int i = 0; i < floats.length; i++) {
        floats[i] = floatGenerator.next();
      }
      return floats;
    }
  }

  public static class DoubleArrayGenerator extends Generator<double[]> {
    private final Generator<Integer> sizeGenerator;
    private final Generator<Double> valueGenerator;

    public DoubleArrayGenerator(Generator<Integer> sizeGenerator, Generator<Double> valueGenerator) {
      this.sizeGenerator = sizeGenerator;
      this.valueGenerator = valueGenerator;
    }

    @Override
    public double[] next() {
      double[] doubles = new double[sizeGenerator.next()];
      for(int i = 0; i < doubles.length; i++) {
        doubles[i] = valueGenerator.next();
      }
      return doubles;
    }
  }

  public static class LongArrayGenerator extends Generator<long[]> {
    private final Generator<Integer> sizeGenerator;
    private final Generator<Long> valueGenerator;

    public LongArrayGenerator(Generator<Integer> sizeGenerator, Generator<Long> valueGenerator) {
      this.sizeGenerator = sizeGenerator;
      this.valueGenerator = valueGenerator;
    }

    @Override
    public long[] next() {
      long[] longs = new long[sizeGenerator.next()];
      for(int i = 0; i < longs.length; i++) {
        longs[i] = valueGenerator.next();
      }
      return longs;
    }
  }
}
