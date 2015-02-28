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
package com.github.jasonruckman.model;

public class AllArrays {
  private boolean[] booleans;
  private short[] shorts;
  private char[] chars;
  private int[] ints;
  private long[] longs;
  private float[] floats;
  private double[] doubles;

  public boolean[] getBooleans() {
    return booleans;
  }

  public void setBooleans(boolean[] booleans) {
    this.booleans = booleans;
  }

  public short[] getShorts() {
    return shorts;
  }

  public void setShorts(short[] shorts) {
    this.shorts = shorts;
  }

  public char[] getChars() {
    return chars;
  }

  public void setChars(char[] chars) {
    this.chars = chars;
  }

  public int[] getInts() {
    return ints;
  }

  public void setInts(int[] ints) {
    this.ints = ints;
  }

  public long[] getLongs() {
    return longs;
  }

  public void setLongs(long[] longs) {
    this.longs = longs;
  }

  public float[] getFloats() {
    return floats;
  }

  public void setFloats(float[] floats) {
    this.floats = floats;
  }

  public double[] getDoubles() {
    return doubles;
  }

  public void setDoubles(double[] doubles) {
    this.doubles = doubles;
  }
}