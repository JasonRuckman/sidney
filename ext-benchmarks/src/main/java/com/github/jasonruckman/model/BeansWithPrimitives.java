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


public class BeansWithPrimitives {
  @Encode(Encoding.BITMAP)
  private boolean first;
  @Encode(Encoding.GROUPVARINT)
  private long second;
  @Encode(Encoding.GROUPVARINT)
  private long third;
  @Encode(Encoding.DELTABITPACKINGHYBRID)
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
