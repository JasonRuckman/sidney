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

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;
import com.github.jasonruckman.sidney.core.io.input.Input;

public class Plain {
  public static class PlainFloat32Decoder extends IndirectDecoder implements Float32Decoder {
    @Override
    public float nextFloat() {
      return input.readFloat();
    }

    @Override
    public float[] nextFloats(int num) {
      return input.readFloats(num);
    }
  }

  public static class PlainFloat32Encoder extends IndirectEncoder implements Float32Encoder {
    @Override
    public void writeFloat(float value) {
      output.writeFloat(value);
    }

    @Override
    public void writeFloats(float[] floats) {
      output.writeFloats(floats);
    }
  }
}