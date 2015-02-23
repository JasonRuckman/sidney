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
package com.github.jasonruckman.sidney.core.io.float64;

import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.int64.Int64Decoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Encoder;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class Plain {
  public static class PlainFloat64Decoder implements Float64Decoder {
    private final Int64Decoder decoder = new com.github.jasonruckman.sidney.core.io.int64.Plain.PlainInt64Decoder();

    @Override
    public double nextDouble() {
      return Double.longBitsToDouble(decoder.nextLong());
    }

    @Override
    public double[] nextDoubles(int num) {
      double[] results = new double[num];
      for (int i = 0; i < num; i++) {
        results[i] = nextDouble();
      }
      return results;
    }

    @Override
    public void initialize(Input input) {
      decoder.initialize(input);
    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new Default.DefaultColumnLoadStrategy();
    }
  }

  public static class PlainFloat64Encoder implements Float64Encoder {
    private final Int64Encoder encoder = new com.github.jasonruckman.sidney.core.io.int64.Plain.PlainInt64Encoder();

    @Override
    public void writeDouble(double value, Output output) {
      encoder.writeLong(Double.doubleToLongBits(value), output);
    }

    @Override
    public void writeDoubles(double[] values, Output output) {
      for (double value : values) {
        writeDouble(value, output);
      }
    }

    @Override
    public void reset() {
      encoder.reset();
    }

    @Override
    public void flush(Output output) {
      encoder.flush(output);
    }

    @Override
    public ColumnWriteStrategy strategy() {
      return new Default.DefaultColumnWriteStrategy();
    }
  }
}
