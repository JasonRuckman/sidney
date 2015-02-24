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

import com.github.jasonruckman.sidney.core.util.Bytes;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Output {
  private static final Charset charset = Charset.forName("UTF-8");
  protected byte[] buffer = new byte[64];
  private int position = 0;

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getPositionAndIncrement(int size) {
    int pos = position;
    position += size;
    return pos;
  }

  public void writeBoolean(boolean value) {
    ensureCapacity(1);
    writeByte((byte) ((value) ? 1 : 0));
  }

  public void writeBooleans(boolean[] values) {
    for (boolean value : values) {
      writeBoolean(value);
    }
  }

  public void writeByte(byte value) {
    ensureCapacity(1);
    buffer[getPositionAndIncrement(1)] = value;
  }

  public void writeChar(char value) {
    ensureCapacity(2);
    Bytes.writeChar(value, buffer, getPositionAndIncrement(2));
  }

  public void writeChars(char[] values) {
    for (char value : values) {
      writeChar(value);
    }
  }

  public void writeShort(short value) {
    ensureCapacity(2);
    Bytes.writeShort(value, buffer, getPositionAndIncrement(2));
  }

  public void writeShorts(short[] values) {
    for (short value : values) {
      writeShort(value);
    }
  }

  public void writeInt(int value) {
    ensureCapacity(4);
    Bytes.writeInt(value, buffer, getPositionAndIncrement(4));
  }

  public void writeInts(int[] values) {
    for (int value : values) {
      writeInt(value);
    }
  }

  public void writeInts(int[] values, int position, int length) {
    for (int i = position; i < (position + length); i++) {
      writeInt(values[i]);
    }
  }

  public void writeLong(long value) {
    ensureCapacity(8);
    Bytes.writeLong(value, buffer, getPositionAndIncrement(8));
  }

  public void writeLongs(long[] values) {
    for (long value : values) {
      writeLong(value);
    }
  }

  public void writeFloat(float value) {
    writeInt(Float.floatToIntBits(value));
  }

  public void writeFloats(float[] values) {
    for (float value : values) {
      writeFloat(value);
    }
  }

  public void writeDouble(double value) {
    writeLong(Double.doubleToLongBits(value));
  }

  public void writeDoubles(double[] values) {
    for (double value : values) {
      writeDouble(value);
    }
  }

  public void writeBytes(byte[] bytes) {
    writeBytes(bytes, 0, bytes.length);
  }

  public void writeBytes(byte[] bytes, int offset, int length) {
    ensureCapacity(length);
    System.arraycopy(bytes, offset, buffer, getPositionAndIncrement(length), length);
  }

  public void writeUtf8(String value) {
    byte[] bytes = value.getBytes(charset);

    writeInt(bytes.length);
    writeBytes(bytes);
  }

  public void flush(OutputStream outputStream) {
    try {
      Bytes.writeIntToStream(getPosition(), outputStream);
      outputStream.write(buffer, 0, getPosition());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void clear() {
    setPosition(0);
  }

  protected void ensureCapacity(int bytes) {
    if (getPosition() + bytes >= buffer.length) {
      int newSize = Math.max(buffer.length * 2, (getPosition() + bytes) * 2);
      byte[] newBuffer = new byte[newSize];
      System.arraycopy(buffer, 0, newBuffer, 0, getPosition());
      buffer = newBuffer;
    }
  }
}