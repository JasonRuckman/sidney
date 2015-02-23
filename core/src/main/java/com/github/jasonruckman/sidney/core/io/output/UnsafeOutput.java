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
package com.github.jasonruckman.sidney.core.io.output;

import com.github.jasonruckman.sidney.core.util.UnsafeUtil;
import sun.misc.Unsafe;

import java.io.OutputStream;

//TODO: Finish out this class
public class UnsafeOutput extends Output {
  private final Unsafe unsafe = UnsafeUtil.unsafe();

  public void writeBoolean(boolean value) {
    writeByte((byte)((value) ? 1 : 0));
  }

  @Override
  public void writeBooleans(boolean[] values) {
    ensureCapacity(values.length);
    unsafe.copyMemory(values,
        UnsafeUtil.boolArrayBaseOffset,
        buffer,
        UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(values.length),
        values.length
    );
  }

  @Override
  public void writeByte(byte value) {
    ensureCapacity(1);
    unsafe.putByte(buffer, UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(1), value);
  }

  @Override
  public void writeChar(char value) {
    super.writeChar(value);
  }

  @Override
  public void writeChars(char[] values) {
    super.writeChars(values);
  }

  @Override
  public void writeShort(short value) {
    super.writeShort(value);
  }

  @Override
  public void writeShorts(short[] values) {
    super.writeShorts(values);
  }

  @Override
  public void writeInt(int value) {
    ensureCapacity(4);
    unsafe.putInt(buffer, UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(4), value);
  }

  @Override
  public void writeInts(int[] values) {
    int size = values.length << 2;
    ensureCapacity(size);
    unsafe.copyMemory(
        values, UnsafeUtil.intArrayBaseOffset, buffer, UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(size), values.length
    );
  }

  @Override
  public void writeInts(int[] values, int position, int length) {
    int size = length << 2;
    ensureCapacity(size);
    unsafe.copyMemory(
        values, UnsafeUtil.intArrayBaseOffset + position, buffer, UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(size), size
    );
  }

  @Override
  public void writeLong(long value) {
    ensureCapacity(8);
    unsafe.putLong(buffer, UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(8), value);
  }

  @Override
  public void writeLongs(long[] values) {
    super.writeLongs(values);
  }

  @Override
  public void writeFloat(float value) {
    super.writeFloat(value);
  }

  @Override
  public void writeFloats(float[] values) {
    super.writeFloats(values);
  }

  @Override
  public void writeDouble(double value) {
    ensureCapacity(8);
    unsafe.putDouble(buffer, UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(8), value);
  }

  @Override
  public void writeDoubles(double[] values) {
    int size = 8 * values.length;
    ensureCapacity(size);
    unsafe.copyMemory(values, UnsafeUtil.doubleArrayBaseOffset, buffer, UnsafeUtil.byteArrayBaseOffset + getPositionAndIncrement(size), size);
  }

  @Override
  public void writeBytes(byte[] bytes) {
    super.writeBytes(bytes);
  }

  @Override
  public void writeBytes(byte[] bytes, int offset, int length) {
    super.writeBytes(bytes, offset, length);
  }

  @Override
  public void writeUtf8(String value) {
    super.writeUtf8(value);
  }

  @Override
  public void flush(OutputStream outputStream) {
    super.flush(outputStream);
  }
}
