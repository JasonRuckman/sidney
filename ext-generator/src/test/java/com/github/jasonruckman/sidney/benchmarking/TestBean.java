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
package com.github.jasonruckman.sidney.benchmarking;

public class TestBean {
  private int first;
  private long second;
  private boolean third;
  private String fourth;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TestBean bean = (TestBean) o;

    if (first != bean.first) return false;
    if (second != bean.second) return false;
    if (third != bean.third) return false;
    if (fourth != null ? !fourth.equals(bean.fourth) : bean.fourth != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = first;
    result = 31 * result + (int) (second ^ (second >>> 32));
    result = 31 * result + (third ? 1 : 0);
    result = 31 * result + (fourth != null ? fourth.hashCode() : 0);
    return result;
  }

  public boolean isThird() {
    return third;
  }

  public void setThird(boolean third) {
    this.third = third;
  }

  public int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  public long getSecond() {
    return second;
  }

  public void setSecond(long second) {
    this.second = second;
  }

  @Override
  public String toString() {
    return "TestBean{" +
        "first=" + first +
        ", second=" + second +
        ", third=" + third +
        '}';
  }

  public String getFourth() {
    return fourth;
  }

  public void setFourth(String fourth) {
    this.fourth = fourth;
  }
}