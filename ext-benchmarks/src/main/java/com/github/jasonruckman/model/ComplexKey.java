package com.github.jasonruckman.model;

import com.github.jasonruckman.sidney.core.Encode;
import com.github.jasonruckman.sidney.core.io.Encoding;

public class ComplexKey {
  @Encode(Encoding.DELTABITPACKINGHYBRID)
  private int first;
  private double second;

  public int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  public double getSecond() {
    return second;
  }

  public void setSecond(double second) {
    this.second = second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ComplexKey that = (ComplexKey) o;

    if (first != that.first) return false;
    if (Double.compare(that.second, second) != 0) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = first;
    temp = Double.doubleToLongBits(second);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
