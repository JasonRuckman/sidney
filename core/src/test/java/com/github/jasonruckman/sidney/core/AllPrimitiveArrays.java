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

import java.util.Arrays;

public class AllPrimitiveArrays {
  private boolean[] booleans;
  private short[] shorts;
  private char[] chars;
  private int[] ints;
  private long[] longs;
  private float[] floats;
  private double[] doubles;

  public AllPrimitiveArrays() {

  }

  public AllPrimitiveArrays(boolean[] booleans, short[] shorts, char[] chars, int[] ints, long[] longs, float[] floats, double[] doubles) {
    this.booleans = booleans;
    this.shorts = shorts;
    this.chars = chars;
    this.ints = ints;
    this.longs = longs;
    this.floats = floats;
    this.doubles = doubles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AllPrimitiveArrays that = (AllPrimitiveArrays) o;

    if (!Arrays.equals(booleans, that.booleans)) return false;
    if (!Arrays.equals(chars, that.chars)) return false;
    if (!Arrays.equals(doubles, that.doubles)) return false;
    if (!Arrays.equals(floats, that.floats)) return false;
    if (!Arrays.equals(ints, that.ints)) return false;
    if (!Arrays.equals(longs, that.longs)) return false;
    if (!Arrays.equals(shorts, that.shorts)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = booleans != null ? Arrays.hashCode(booleans) : 0;
    result = 31 * result + (shorts != null ? Arrays.hashCode(shorts) : 0);
    result = 31 * result + (chars != null ? Arrays.hashCode(chars) : 0);
    result = 31 * result + (ints != null ? Arrays.hashCode(ints) : 0);
    result = 31 * result + (longs != null ? Arrays.hashCode(longs) : 0);
    result = 31 * result + (floats != null ? Arrays.hashCode(floats) : 0);
    result = 31 * result + (doubles != null ? Arrays.hashCode(doubles) : 0);
    return result;
  }
}