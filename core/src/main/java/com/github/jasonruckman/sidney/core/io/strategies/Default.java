package com.github.jasonruckman.sidney.core.io.strategies;

import com.github.jasonruckman.sidney.core.io.Decoder;
import com.github.jasonruckman.sidney.core.io.Encoder;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;

import java.io.InputStream;
import java.io.OutputStream;

public class Default {
  public static class DefaultColumnLoadStrategy implements ColumnLoadStrategy {
    @Override
    public void load(Decoder decoder, Input input, InputStream stream) {
      input.initialize(stream);
      decoder.initialize(input);
    }
  }

  public static class DefaultColumnWriteStrategy implements ColumnWriteStrategy {
    @Override
    public void write(Encoder encoder, Output output, OutputStream outputStream) {
      encoder.flush(output);
      output.flush(outputStream);
      output.clear();
      encoder.reset();
    }
  }
}
