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
    return unsafe.getByte(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(1));
  }

  @Override
  public char readChar() {
    return super.readChar();
  }

  @Override
  public char[] readChars(int num) {
    return super.readChars(num);
  }

  @Override
  public short readShort() {
    return super.readShort();
  }

  @Override
  public short[] readShorts(int num) {
    return super.readShorts(num);
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
    return super.readLongs(num);
  }

  @Override
  public float readFloat() {
    return super.readFloat();
  }

  @Override
  public float[] readFloats(int num) {
    return super.readFloats(num);
  }

  @Override
  public double readDouble() {
    require(8);
    return unsafe.getDouble(buffer, UnsafeUtil.byteArrayBaseOffset + getAndIncrementPosition(8));
  }

  @Override
  public double[] readDoubles(int num) {
    return super.readDoubles(num);
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
