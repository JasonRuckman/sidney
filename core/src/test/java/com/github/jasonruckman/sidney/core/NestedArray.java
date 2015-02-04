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

import java.util.Arrays;

public class NestedArray<T> {
  private T[] array;

  public T[] getArray() {
    return array;
  }

  public void setArray(T[] array) {
    this.array = array;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NestedArray that = (NestedArray) o;

    if (!Arrays.equals(array, that.array)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return array != null ? Arrays.hashCode(array) : 0;
  }
}
