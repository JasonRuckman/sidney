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