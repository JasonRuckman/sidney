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
package com.github.jasonruckman.sidney.core.serde;

import com.carrotsearch.hppc.ObjectIntMap;
import com.carrotsearch.hppc.ObjectIntOpenIdentityHashMap;
import com.github.jasonruckman.sidney.core.Configuration;

/**
 * A class for tracking references and generating values for them
 */
public class References {
  private final ObjectIntMap<Object> objectToValue = new ObjectIntOpenIdentityHashMap<>(128);
  private final IntObjMap valueToObject = new IntObjMap();

  private int counter = 1;

  public int nextCounter() {
    return counter++;
  }

  /**
   * Add this value to reference tracking
   *
   * @param value a value to track
   */
  public void addReference(Object value, int reference) {
    valueToObject.put(value, reference);
  }

  /**
   * Adds this value to reference tracking if it doesn't exist, otherwise returns the value for it
   *
   * @param value a value to track
   * @return zero if not found, a positive value otherwise
   */
  public int trackObject(Object value) {
    int val = objectToValue.get(value);
    if (val == 0) {
      objectToValue.put(value, counter++);
    }
    return val;
  }

  /**
   * Get the value for this reference
   *
   * @param reference a int reference
   * @return a value
   */
  public Object getReference(int reference) {
    return valueToObject.get(reference);
  }

  /**
   * Reset this reference tracking
   */
  public void clear() {
    objectToValue.clear();
    valueToObject.clear();
    counter = 1;
  }

  private static class IntObjMap {
    private Object[] arr = new Object[128];
    private int length = 0;

    public void put(Object value, int reference) {
      if (reference >= arr.length) {
        resize(reference);
      }
      length++;
      arr[reference] = value;
    }

    public void clear() {
      arr = new Object[128];
      length = 0;
    }

    public Object get(int reference) {
      return arr[reference];
    }

    private void resize(int newSize) {
      Object[] newArr = new Object[newSize * 2];
      System.arraycopy(arr, 0, newArr, 0, length);
      arr = newArr;
    }
  }

  /**
   * A read context that peeks at null markers, checks if it needs to pull a reference, otherwise delegates
   */
  public static class ReferenceTrackingReadContext extends Contexts.ReadContextImpl {
    public ReferenceTrackingReadContext(Configuration conf) {
      super(conf);
    }

    @Override
    public boolean shouldReadValue() {
      return true;
    }

    public boolean readNullMarker() {
      return super.shouldReadValue();
    }
  }

  public static class ReferenceTrackingWriteContext extends Contexts.WriteContextImpl {
    public ReferenceTrackingWriteContext(PageHeader pageHeader, Configuration conf) {
      super(pageHeader, conf);
    }

    @Override
    public <T> boolean shouldWriteValue(T value) {
      return true;
    }
  }
}