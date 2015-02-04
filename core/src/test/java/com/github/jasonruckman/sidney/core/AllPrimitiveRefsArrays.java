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

public class AllPrimitiveRefsArrays {
  private Boolean[] booleans;
  private Byte[] bytes;
  private Short[] shorts;
  private Character[] characters;
  private Integer[] ints;
  private Long[] longs;
  private Float[] floats;
  private Double[] doubles;

  public AllPrimitiveRefsArrays() {

  }

  public AllPrimitiveRefsArrays(Boolean[] booleans, Byte[] bytes, Short[] shorts, Character[] characters, Integer[] ints, Long[] longs, Float[] floats, Double[] doubles) {
    this.booleans = booleans;
    this.bytes = bytes;
    this.shorts = shorts;
    this.characters = characters;
    this.ints = ints;
    this.longs = longs;
    this.floats = floats;
    this.doubles = doubles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AllPrimitiveRefsArrays that = (AllPrimitiveRefsArrays) o;

    if (!Arrays.equals(booleans, that.booleans)) return false;
    if (!Arrays.equals(bytes, that.bytes)) return false;
    if (!Arrays.equals(characters, that.characters)) return false;
    if (!Arrays.equals(doubles, that.doubles)) return false;
    if (!Arrays.equals(floats, that.floats)) return false;
    if (!Arrays.equals(ints, that.ints)) return false;
    if (!Arrays.equals(longs, that.longs)) return false;
    if (!Arrays.equals(shorts, that.shorts)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = booleans != null ? Arrays.hashCode(booleans) : 0;
    result = 31 * result + (bytes != null ? Arrays.hashCode(bytes) : 0);
    result = 31 * result + (shorts != null ? Arrays.hashCode(shorts) : 0);
    result = 31 * result + (characters != null ? Arrays.hashCode(characters) : 0);
    result = 31 * result + (ints != null ? Arrays.hashCode(ints) : 0);
    result = 31 * result + (longs != null ? Arrays.hashCode(longs) : 0);
    result = 31 * result + (floats != null ? Arrays.hashCode(floats) : 0);
    result = 31 * result + (doubles != null ? Arrays.hashCode(doubles) : 0);
    return result;
  }
}
