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
package com.github.jasonruckman.sidney.core.io.float32;

import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class Plain {
  public static class PlainFloat32Decoder implements Float32Decoder {
    private Int32Decoder int32Decoder = new com.github.jasonruckman.sidney.core.io.int32.Plain.PlainInt32Decoder();

    @Override
    public float nextFloat() {
      return Float.intBitsToFloat(int32Decoder.nextInt());
    }

    @Override
    public float[] nextFloats(int num) {
      float[] floats = new float[num];
      for (int i = 0; i < num; i++) {
        floats[i] = nextFloat();
      }
      return floats;
    }

    @Override
    public void initialize(Input input) {
      int32Decoder.initialize(input);
    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new Default.DefaultColumnLoadStrategy();
    }
  }

  public static class PlainFloat32Encoder implements Float32Encoder {
    private final Int32Encoder encoder = new com.github.jasonruckman.sidney.core.io.int32.Plain.PlainInt32Encoder();

    @Override
    public void writeFloat(float value, Output output) {
      encoder.writeInt(Float.floatToIntBits(value), output);
    }

    @Override
    public void writeFloats(float[] floats, Output output) {
      for (float v : floats) {
        writeFloat(v, output);
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