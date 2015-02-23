package com.github.jasonruckman.sidney.core.io.strategies;

import com.github.jasonruckman.sidney.core.io.Decoder;
import com.github.jasonruckman.sidney.core.io.input.Input;

import java.io.InputStream;

public interface ColumnLoadStrategy {
  void load(Decoder decoder, Input input, InputStream stream);
}