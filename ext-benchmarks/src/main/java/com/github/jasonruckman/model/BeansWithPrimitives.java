package com.github.jasonruckman.model;

import com.github.jasonruckman.sidney.core.Encode;
import com.github.jasonruckman.sidney.core.io.Encoding;

/**
 *Generator<BeansWithPrimitives> bwpGenerator = beansWithPrimitivesGenerator
 .field(bwp.isFirst(), percentageTrue(0.7))
 .field(bwp.getSecond(), range(0L, 500L))
 .field(bwp.getThird(), incrementingBy(1L))
 .field(bwp.getFourth(), range(0, 5))
 .field(bwp.getFifth(), always(0.5D).build();
 */
public class BeansWithPrimitives {
  @Encode(Encoding.BITMAP)
  private boolean first;
  @Encode(Encoding.GROUPVARINT)
  private long second;
  @Encode(Encoding.GROUPVARINT)
  private long third;
  @Encode(Encoding.BITPACKED)
  private int fourth;
  @Encode(Encoding.RLE)
  private double fifth;

  public boolean isFirst() {
    return first;
  }

  public void setFirst(boolean first) {
    this.first = first;
  }

  public long getSecond() {
    return second;
  }

  public void setSecond(long second) {
    this.second = second;
  }

  public long getThird() {
    return third;
  }

  public void setThird(long third) {
    this.third = third;
  }

  public int getFourth() {
    return fourth;
  }

  public void setFourth(int fourth) {
    this.fourth = fourth;
  }

  public double getFifth() {
    return fifth;
  }

  public void setFifth(double fifth) {
    this.fifth = fifth;
  }
}
