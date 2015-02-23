package com.github.jasonruckman.sidney.core.io;

public interface TriConsumer<X, Y, Z> {
  void accept(X x, Y y, Z z);
}
