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

import com.github.jasonruckman.sidney.core.io.Encoding;

import java.util.Arrays;

public class AllPrimitives {
  private boolean first;
  @Encode(Encoding.BITPACKED)
  private int second;
  private char third;
  private short fourth;
  private byte fifth;
  private long sixth;
  private float seventh;
  private double eighth;
  private byte[] ninth;
  private String tenth;
  private SomeEnum eleventh;

  public AllPrimitives() {

  }

  public AllPrimitives(boolean first, int second, char third, short fourth, byte fifth, long sixth,
                       float seventh, double eighth, byte[] ninth, String tenth, SomeEnum eleventh) {
    this.first = first;
    this.second = second;
    this.third = third;
    this.fourth = fourth;
    this.fifth = fifth;
    this.sixth = sixth;
    this.seventh = seventh;
    this.eighth = eighth;
    this.ninth = ninth;
    this.tenth = tenth;
    this.eleventh = eleventh;
  }

  public boolean isFirst() {
    return first;
  }

  public int getSecond() {
    return second;
  }

  public char getThird() {
    return third;
  }

  public short getFourth() {
    return fourth;
  }

  public byte getFifth() {
    return fifth;
  }

  public long getSixth() {
    return sixth;
  }

  public float getSeventh() {
    return seventh;
  }

  public double getEighth() {
    return eighth;
  }

  public byte[] getNinth() {
    return ninth;
  }

  public String getTenth() {
    return tenth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AllPrimitives that = (AllPrimitives) o;

    if (Double.compare(that.eighth, eighth) != 0) return false;
    if (fifth != that.fifth) return false;
    if (first != that.first) return false;
    if (fourth != that.fourth) return false;
    if (second != that.second) return false;
    if (Float.compare(that.seventh, seventh) != 0) return false;
    if (sixth != that.sixth) return false;
    if (third != that.third) return false;
    if (eleventh != that.eleventh) return false;
    if (!Arrays.equals(ninth, that.ninth)) return false;
    if (tenth != null ? !tenth.equals(that.tenth) : that.tenth != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = (first ? 1 : 0);
    result = 31 * result + second;
    result = 31 * result + (int) third;
    result = 31 * result + (int) fourth;
    result = 31 * result + (int) fifth;
    result = 31 * result + (int) (sixth ^ (sixth >>> 32));
    result = 31 * result + (seventh != +0.0f ? Float.floatToIntBits(seventh) : 0);
    temp = Double.doubleToLongBits(eighth);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (ninth != null ? Arrays.hashCode(ninth) : 0);
    result = 31 * result + (tenth != null ? tenth.hashCode() : 0);
    result = 31 * result + (eleventh != null ? eleventh.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "AllPrimitives{" +
        "first=" + first +
        ", second=" + second +
        ", third=" + third +
        ", fourth=" + fourth +
        ", fifth=" + fifth +
        ", sixth=" + sixth +
        ", seventh=" + seventh +
        ", eighth=" + eighth +
        ", ninth=" + Arrays.toString(ninth) +
        ", tenth='" + tenth + '\'' +
        ", eleventh=" + eleventh +
        '}';
  }
}
