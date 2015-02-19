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
package com.github.jasonruckman.sidney.generator;

import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Generator<T> {
  public abstract T next();

  public final List<T> list(int count) {
    List<T> list = new ArrayList<>(count);
    for (T x : stream(count)) {
      list.add(x);
    }
    return list;
  }

  public final Iterable<T> stream(int count) {
    return Iterables.limit(stream(), count);
  }

  public final Iterable<T> stream() {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new Iterator<T>() {
          @Override
          public boolean hasNext() {
            return true;
          }

          @Override
          public T next() {
            return Generator.this.next();
          }

          @Override
          public void remove() {

          }
        };
      }
    };
  }
}