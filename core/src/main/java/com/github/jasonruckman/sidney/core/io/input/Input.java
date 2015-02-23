package com.github.jasonruckman.sidney.core.io.input;

import com.github.jasonruckman.sidney.core.util.Bytes;

import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.charset.Charset;

public class Input {
  private static final Charset charset = Charset.forName("UTF-8");
  protected byte[] buffer = new byte[64];
  private int position;

  public void setPosition(int position) {
    this.position = position;
  }

  public int getAndIncrementPosition(int size) {
    int pos = position;
    position += size;
    return pos;
  }

  public boolean readBoolean() {
    return readByte() > 0;
  }

  public boolean[] readBooleans(int num) {
    boolean[] booleans = new boolean[num];
    for (int i = 0; i < num; i++) {
      booleans[i] = readBoolean();
    }
    return booleans;
  }

  public byte readByte() {
    require(1);
    return buffer[getAndIncrementPosition(1)];
  }

  public char readChar() {
    require(2);
    return Bytes.readChar(buffer, getAndIncrementPosition(2));
  }

  public char[] readChars(int num) {
    char[] chars = new char[num];
    for (int i = 0; i < num; i++) {
      chars[i] = readChar();
    }
    return chars;
  }

  public short readShort() {
    require(2);
    return Bytes.readShort(buffer, getAndIncrementPosition(2));
  }

  public short[] readShorts(int num) {
    short[] shorts = new short[num];
    for (int i = 0; i < num; i++) {
      shorts[i] = readShort();
    }
    return shorts;
  }

  public int readInt() {
    require(4);
    return Bytes.readInt(buffer, getAndIncrementPosition(4));
  }

  public int[] readInts(int num) {
    int[] ints = new int[num];
    for (int i = 0; i < num; i++) {
      ints[i] = readInt();
    }
    return ints;
  }

  public long readLong() {
    require(8);
    return Bytes.readLong(buffer, getAndIncrementPosition(8));
  }

  public long[] readLongs(int num) {
    long[] longs = new long[num];
    for (int i = 0; i < num; i++) {
      longs[i] = readLong();
    }
    return longs;
  }

  public float readFloat() {
    return Float.intBitsToFloat(readInt());
  }

  public float[] readFloats(int num) {
    float[] floats = new float[num];
    for (int i = 0; i < num; i++) {
      floats[i] = readFloat();
    }
    return floats;
  }

  public double readDouble() {
    return Double.longBitsToDouble(readLong());
  }

  public double[] readDoubles(int num) {
    double[] doubles = new double[num];
    for (int i = 0; i < num; i++) {
      doubles[i] = readDouble();
    }
    return doubles;
  }

  public String readUtf8() {
    int length = readInt();
    byte[] bytes = readBytes(length);
    return new String(bytes, charset);
  }

  public byte[] readBytes(int num) {
    require(num);

    byte[] bytes = new byte[num];
    System.arraycopy(buffer, getAndIncrementPosition(num), bytes, 0, num);
    return bytes;
  }

  public void initialize(InputStream inputStream) {
    try {
      setPosition(0);
      int size = Bytes.readIntFromStream(inputStream);
      resizeIfNecessary(size);
      Bytes.readFully(buffer, inputStream, size);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  protected void require(int size) {
    if (position + size > buffer.length) {
      throw new BufferUnderflowException();
    }
  }

  protected void resizeIfNecessary(int size) {
    if (buffer.length < size) {
      byte[] buf = new byte[size];
      System.arraycopy(buffer, 0, buf, 0, buffer.length);
      buffer = buf;
    }
  }
}