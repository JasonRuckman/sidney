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