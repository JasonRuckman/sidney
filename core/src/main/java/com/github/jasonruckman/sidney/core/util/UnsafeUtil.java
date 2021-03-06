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
package com.github.jasonruckman.sidney.core.util;

import sun.misc.Unsafe;

public class UnsafeUtil {
  public static final long boolArrayBaseOffset;
  public static final long byteArrayBaseOffset;
  public static final long floatArrayBaseOffset;
  public static final long doubleArrayBaseOffset;
  public static final long intArrayBaseOffset;
  public static final long longArrayBaseOffset;
  public static final long shortArrayBaseOffset;
  public static final long charArrayBaseOffset;
  private static final Unsafe unsafe;

  static {
    java.lang.reflect.Field field = null;
    try {
      field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      unsafe = (Unsafe) field.get(null);
      boolArrayBaseOffset = unsafe.arrayBaseOffset(boolean[].class);
      byteArrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
      charArrayBaseOffset = unsafe.arrayBaseOffset(char[].class);
      shortArrayBaseOffset = unsafe.arrayBaseOffset(short[].class);
      intArrayBaseOffset = unsafe.arrayBaseOffset(int[].class);
      floatArrayBaseOffset = unsafe.arrayBaseOffset(float[].class);
      longArrayBaseOffset = unsafe.arrayBaseOffset(long[].class);
      doubleArrayBaseOffset = unsafe.arrayBaseOffset(double[].class);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static Unsafe unsafe() {
    return unsafe;
  }
}
