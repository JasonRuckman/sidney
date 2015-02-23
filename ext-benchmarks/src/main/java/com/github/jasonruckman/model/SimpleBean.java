package com.github.jasonruckman.model;

import com.github.jasonruckman.sidney.core.Encode;
import com.github.jasonruckman.sidney.core.io.Encoding;

public class SimpleBean {
  @Encode(Encoding.DELTABITPACKINGHYBRID)
  private int first;
  @Encode(Encoding.RLE)
  private short second;
  @Encode(Encoding.RLE)
  private float third;
  private String fourth;
  private double fifth;

  public short getSecond() {
    return second;
  }

  public void setSecond(short second) {
    this.second = second;
  }

  public int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  public float getThird() {
    return third;
  }

  public void setThird(float third) {
    this.third = third;
  }

  public String getFourth() {
    return fourth;
  }

  public void setFourth(String fourth) {
    this.fourth = fourth;
  }

  public double getFifth() {
    return fifth;
  }

  public void setFifth(double fifth) {
    this.fifth = fifth;
  }
}
