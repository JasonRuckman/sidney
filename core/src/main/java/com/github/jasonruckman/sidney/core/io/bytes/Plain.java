package com.github.jasonruckman.sidney.core.io.bytes;

import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class Plain {
  public static class ByteArrayDecoder implements BytesDecoder {
    private Input input;

    @Override
    public byte[] readBytes(int num) {
      int length = input.readInt();
      return input.readBytes(length);
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

  public static class ByteArrayEncoder implements BytesEncoder {
    @Override
    public void writeBytes(byte[] bytes, Output output) {
      writeBytes(bytes, 0, bytes.length, output);
    }

    @Override
    public void writeBytes(byte[] bytes, int offset, int length, Output output) {
      output.writeInt(length);
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
