package com.github.jasonruckman.sidney.core.io.bytes;

import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class RawBytes {
  public static class RawBytesDecoder implements BytesDecoder {
    private Input input;

    @Override
    public byte[] readBytes(int num) {
      return input.readBytes(num);
    }

    @Override
    public void initialize(Input input) {
      this.input = input;
    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new Default.DefaultColumnLoadStrategy();
    }
  }

  public static class RawBytesEncoder implements BytesEncoder {
    @Override
    public void writeBytes(byte[] bytes, Output output) {
      output.writeBytes(bytes);
    }

    @Override
    public void writeBytes(byte[] bytes, int offset, int length, Output output) {
      output.writeBytes(bytes, offset, length);
    }

    @Override
    public void reset() {

    }

    @Override
    public void flush(Output output) {

    }

    @Override
    public ColumnWriteStrategy strategy() {
      return new Default.DefaultColumnWriteStrategy();
    }
  }
}
