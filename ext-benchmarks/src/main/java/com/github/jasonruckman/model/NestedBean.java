package com.github.jasonruckman.model;

import com.github.jasonruckman.sidney.core.Encode;
import com.github.jasonruckman.sidney.core.io.Encoding;

import java.util.HashMap;
import java.util.Map;

public class NestedBean {
  private Map<ComplexKey, BeansWithPrimitives> first = new HashMap<>();
  @Encode(Encoding.RLE)
  private int second;
  private int third;
  private float fourth;

  public int getSecond() {
    return second;
  }

  public void setSecond(int second) {
    this.second = second;
  }

  public int getThird() {
    return third;
  }

  public void setThird(int third) {
    this.third = third;
  }

  public float getFourth() {
    return fourth;
  }

  public void setFourth(float fourth) {
    this.fourth = fourth;
  }

  public Map<ComplexKey, BeansWithPrimitives> getFirst() {
    return first;
  }

  public void setFirst(Map<ComplexKey, BeansWithPrimitives> first) {
    this.first = first;
  }
}