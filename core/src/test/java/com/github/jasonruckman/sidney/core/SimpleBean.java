package com.github.jasonruckman.sidney.core;

public class SimpleBean {
  private int first;

  public int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SimpleBean that = (SimpleBean) o;

    if (first != that.first) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return first;
  }
}
