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
package com.github.jasonruckman.sidney.core.io.input;

import com.github.jasonruckman.sidney.core.util.UnsafeUtil;
import sun.misc.Unsafe;

public class UnsafeInput extends Input {
  private final Unsafe unsafe = UnsafeUtil.unsafe();

  @Override
  public boolean[] readBooleans(int num) {
    return super.readBooleans(num);
  }

  @Override
  public byte readByte() {
    require(1);
    return unsafe.getByte(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(1));
  }

  @Override
  public char readChar() {
    require(2);
    return unsafe.getChar(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(2));
  }

  @Override
  public char[] readChars(int num) {
    int size = num << 1;
    require(size);
    char[] chars = new char[num];
    unsafe.copyMemory(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(size), chars, UnsafeUtil.charArrayBaseOffset, size);
    return chars;
  }

  @Override
  public short readShort() {
    require(2);
    return unsafe.getShort(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(2));
  }

  @Override
  public short[] readShorts(int num) {
    require(num * 2);
    short[] shorts = new short[num];
    unsafe.copyMemory(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(num * 2), shorts, UnsafeUtil.shortArrayBaseOffset, num * 2);
    return shorts;
  }

  @Override
  public int readInt() {
    require(4);
    return unsafe.getInt(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(4));
  }

  @Override
  public int[] readInts(int num) {
    int size = num << 2;
    require(size);
    int[] ints = new int[num];
    unsafe.copyMemory(
        buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(size), ints, UnsafeUtil.intArrayBaseOffset, size
    );
    return ints;
  }

  @Override
  public long readLong() {
    require(8);
    return unsafe.getLong(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(8));
  }

  @Override
  public long[] readLongs(int num) {
    int size = num * 8;
    require(size);
    long[] longs = new long[num];
    unsafe.copyMemory(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(size), longs, UnsafeUtil.longArrayBaseOffset, size);
    return longs;
  }

  @Override
  public float readFloat() {
    require(4);
    return unsafe.getFloat(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(4));
  }

  @Override
  public float[] readFloats(int num) {
    int size = num << 2;
    require(size);
    float[] floats = new float[num];
    unsafe.copyMemory(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(size), floats, UnsafeUtil.longArrayBaseOffset, size);
    return floats;
  }

  @Override
  public double readDouble() {
    require(8);
    return unsafe.getDouble(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(8));
  }

  @Override
  public double[] readDoubles(int num) {
    int size = num * 8;
    require(size);
    double[] doubles = new double[num];
    unsafe.copyMemory(
        buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(size), doubles, UnsafeUtil.doubleArrayBaseOffset, size
    );
    return doubles;
  }

  @Override
  public String readUtf8() {
    return super.readUtf8();
  }

  @Override
  public byte[] readBytes(int num) {
    return super.readBytes(num);
  }
}
