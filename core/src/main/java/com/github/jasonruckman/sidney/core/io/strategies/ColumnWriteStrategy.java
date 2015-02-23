package com.github.jasonruckman.sidney.core.io.strategies;

import com.github.jasonruckman.sidney.core.io.Encoder;
import com.github.jasonruckman.sidney.core.io.output.Output;

import java.io.OutputStream;

public interface ColumnWriteStrategy {
  void write(Encoder encoder, Output output, OutputStream outputStream);
}