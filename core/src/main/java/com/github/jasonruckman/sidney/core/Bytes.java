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
package com.github.jasonruckman.sidney.core;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Bytes {
  public static int sizeInBytes(int num, int bitwidth) {
    return num * (int) (Math.ceil(bitwidth / 8D));
  }

  public static void writeIntOn4Bytes(int value, byte[] bytes, int offset) {
    bytes[offset + 3] = (byte) (value >>> 24);
    bytes[offset + 2] = (byte) (value >>> 16);
    bytes[offset + 1] = (byte) (value >>> 8);
    bytes[offset] = (byte) value;
  }

  public static void writeLongOnOneByte(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
  }

  public static void writeLongOnTwoBytes(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
    bytes[offset + 1] = (byte) (value >>> 8);
  }

  public static void writeLongOnThreeBytes(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
    bytes[offset + 1] = (byte) (value >>> 8);
    bytes[offset + 2] = (byte) (value >>> 16);
  }

  public static void writeLongOnFourBytes(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
    bytes[offset + 1] = (byte) (value >>> 8);
    bytes[offset + 2] = (byte) (value >>> 16);
    bytes[offset + 3] = (byte) (value >>> 24);
  }

  public static void writeLongOnFiveBytes(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
    bytes[offset + 1] = (byte) (value >>> 8);
    bytes[offset + 2] = (byte) (value >>> 16);
    bytes[offset + 3] = (byte) (value >>> 24);
    bytes[offset + 4] = (byte) (value >>> 32);
  }

  public static void writeLongOnSixBytes(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
    bytes[offset + 1] = (byte) (value >>> 8);
    bytes[offset + 2] = (byte) (value >>> 16);
    bytes[offset + 3] = (byte) (value >>> 24);
    bytes[offset + 4] = (byte) (value >>> 32);
    bytes[offset + 5] = (byte) (value >>> 40);
  }

  public static void writeLongOnSevenBytes(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
    bytes[offset + 1] = (byte) (value >>> 8);
    bytes[offset + 2] = (byte) (value >>> 16);
    bytes[offset + 3] = (byte) (value >>> 24);
    bytes[offset + 4] = (byte) (value >>> 32);
    bytes[offset + 5] = (byte) (value >>> 40);
    bytes[offset + 6] = (byte) (value >>> 48);
  }

  public static void writeLong(long value, byte[] bytes, int offset) {
    bytes[offset] = (byte) value;
    bytes[offset + 1] = (byte) (value >>> 8);
    bytes[offset + 2] = (byte) (value >>> 16);
    bytes[offset + 3] = (byte) (value >>> 24);
    bytes[offset + 4] = (byte) (value >>> 32);
    bytes[offset + 5] = (byte) (value >>> 40);
    bytes[offset + 6] = (byte) (value >>> 48);
    bytes[offset + 7] = (byte) (value >>> 56);
  }

  public static int readInt(byte[] bytes, int offset) {
    return ((bytes[offset + 3] & 255) << 24) +
        ((bytes[offset + 2] & 255) << 16) +
        ((bytes[offset + 1] & 255) << 8) +
        ((bytes[offset] & 255));
  }

  public static long readLong(byte[] bytes, int offset) {
    return ((((long) bytes[offset + 7]) << 56)
        | (((long) bytes[offset + 6] & 0xff) << 48)
        | (((long) bytes[offset + 5] & 0xff) << 40)
        | (((long) bytes[offset + 4] & 0xff) << 32)
        | (((long) bytes[offset + 3] & 0xff) << 24)
        | (((long) bytes[offset + 2] & 0xff) << 16)
        | (((long) bytes[offset + 1] & 0xff) << 8)
        | (((long) bytes[offset] & 0xff)));
  }

  public static long readLongOnOneByte(byte[] buf, int offset) {
    return ((long) buf[offset]) & 255L;
  }

  public static long readLongOnTwoBytes(byte[] buf, int offset) {
    return (((buf[offset + 1] & 255L) << 8)
        | ((buf[offset] & 255L)));
  }

  public static long readLongOnThreeBytes(byte[] buf, int offset) {
    return (((buf[offset + 2] & 255L) << 16)
        | ((buf[offset + 1] & 255L) << 8)
        | (buf[offset] & 255L));
  }

  public static long readLongOnFourBytes(byte[] buf, int offset) {
    return (((buf[offset + 3] & 255L) << 24)
        | ((buf[offset + 2] & 255L) << 16)
        | ((buf[offset + 1] & 255L) << 8)
        | (buf[offset] & 255L));
  }

  public static long readLongOnFiveBytes(byte[] buf, int offset) {
    return (((buf[offset + 4] & 255L) << 32)
        | ((buf[offset + 3] & 255L) << 24)
        | ((buf[offset + 2] & 255L) << 16)
        | ((buf[offset + 1] & 255L) << 8)
        | (buf[offset] & 255L));
  }

  public static long readLongOnSixBytes(byte[] buf, int offset) {
    return (((buf[offset + 5] & 255L) << 40)
        | ((buf[offset + 4] & 255L) << 32)
        | ((buf[offset + 3] & 255L) << 24)
        | ((buf[offset + 2] & 255L) << 16)
        | ((buf[offset + 1] & 255L) << 8)
        | (buf[offset] & 255L));
  }

  public static long readLongOnSevenBytes(byte[] buf, int offset) {
    return (((buf[offset + 6] & 255L) << 48)
        | ((buf[offset + 5] & 255L) << 40)
        | ((buf[offset + 4] & 255L) << 32)
        | ((buf[offset + 3] & 255L) << 24)
        | ((buf[offset + 2] & 255L) << 16)
        | ((buf[offset + 1] & 255L) << 8)
        | (buf[offset] & 255L));
  }

  public static int readIntFromStream(InputStream inputStream) throws IOException {
    byte[] arr = new byte[4];
    readFully(arr, inputStream);

    return readInt(arr, 0);
  }

  public static void writeBoolToStream(boolean value, OutputStream outputStream) throws IOException {
    outputStream.write((value) ? 1 : 0);
  }

  public static void writeIntToStream(int value, OutputStream outputStream) throws IOException {
    byte[] arr = new byte[4];
    writeIntOn4Bytes(value, arr, 0);
    outputStream.write(arr);
  }

  public static void writeStringToStream(String value, OutputStream outputStream) throws IOException {
    byte[] bytes = value.getBytes(Charset.forName("UTF-8"));
    writeIntToStream(bytes.length, outputStream);
    outputStream.write(bytes);
  }

  public static boolean readBoolFromStream(InputStream inputStream) throws IOException {
    return inputStream.read() > 0;
  }

  public static String readStringFromStream(InputStream inputStream) throws IOException {
    int length = readIntFromStream(inputStream);
    byte[] bytes = new byte[length];
    readFully(bytes, inputStream);
    return new String(bytes, Charset.forName("UTF-8"));
  }

  public static void readFully(byte[] buffer, InputStream is) throws IOException {
    int n = 0;
    while (n < buffer.length) {
      int count = is.read(buffer, n, buffer.length - n);
      if (count < 0)
        throw new EOFException();
      n += count;
    }
  }
}