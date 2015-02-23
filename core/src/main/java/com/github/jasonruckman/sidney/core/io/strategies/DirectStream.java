package com.github.jasonruckman.sidney.core.io.strategies;

import com.github.jasonruckman.sidney.core.io.Decoder;
import com.github.jasonruckman.sidney.core.io.Encoder;
import com.github.jasonruckman.sidney.core.io.StreamReader;
import com.github.jasonruckman.sidney.core.io.StreamWriter;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;

import java.io.InputStream;
import java.io.OutputStream;

public class DirectStream {
  public static class DirectStreamColumnLoadStrategy implements ColumnLoadStrategy {
    @Override
    public void load(Decoder decoder, Input input, InputStream stream) {
      ((StreamReader) decoder).read(stream);
    }
  }

  public static class DirectStreamColumnWriteStrategy implements ColumnWriteStrategy {
    @Override
    public void write(Encoder encoder, Output output, OutputStream outputStream) {
      ((StreamWriter) encoder).write(outputStream);
      encoder.reset();
    }
  }
}