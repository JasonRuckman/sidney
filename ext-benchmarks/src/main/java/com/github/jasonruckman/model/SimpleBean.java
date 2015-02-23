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
